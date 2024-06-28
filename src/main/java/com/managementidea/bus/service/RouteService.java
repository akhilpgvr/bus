package com.managementidea.bus.service;

import com.managementidea.bus.exceptions.BusAlreadyInRouteException;
import com.managementidea.bus.exceptions.RouteNotExistsException;
import com.managementidea.bus.model.backOffice.RouteInfo;
import com.managementidea.bus.model.dtos.BusRouteDTO;
import com.managementidea.bus.model.dtos.request.BookTicketRequest;
import com.managementidea.bus.model.dtos.request.DeleteRouteRequest;
import com.managementidea.bus.model.dtos.request.RouteInfoReq;
import com.managementidea.bus.model.entities.BusInfoEntity;
import com.managementidea.bus.model.entities.BusRoutesEntity;
import com.managementidea.bus.model.repo.BusRoutesRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class RouteService {

    @Autowired
    private BusRoutesRepo busRoutesRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BusService busService;
    @Autowired
    private ModelMapper modelMapper;

    public Void addNewRoute(BusRouteDTO request) {

        String regNo = request.getBusRegNo();
        log.info("checking bus is present");
        BusInfoEntity bus = busService.findByBusRegNo(regNo);

        log.info("Checking if the route is busy for given date");
        checkForBusDuplicateRoutes(regNo, request.getRouteInfo());


        BusRoutesEntity busRoute = new BusRoutesEntity();
        busRoute.setBusRegNo(regNo);
        busRoute.setCreatedOn(LocalDateTime.now());
        busRoute.setLastUpdatedOn(LocalDateTime.now());
        busRoute.setRouteInfo(new RouteInfo());
        request.getRouteInfo().forEach(route-> {
            route.setAvailableSeats(bus.getCapacity());
            BeanUtils.copyProperties(route, busRoute.getRouteInfo());
            busRoutesRepo.save(busRoute);
        });

        return null;
    }

    public void checkForBusDuplicateRoutes(String busRegNo, Set<RouteInfoReq> routeInfo) {

        log.info("checking for busRegNo");
        Criteria regNoCriteria = Criteria.where("busRegNo").is(busRegNo);

        routeInfo.forEach(route-> {
            Query query = new Query();
            log.info("checking for arrivalDate");
            Criteria finalCriteria = regNoCriteria.orOperator(
                    Criteria.where("routeInfo.departureDate").gte(route.getDepartureDate()).andOperator(Criteria.where("routeInfo.arrivalDate").lte(route.getArrivalDate())),
                    Criteria.where("routeInfo.departureDate").lte(route.getDepartureDate()).andOperator(Criteria.where("routeInfo.arrivalDate").gte(route.getDepartureDate())),
                    Criteria.where("routeInfo.departureDate").lte(route.getArrivalDate()).andOperator(Criteria.where("routeInfo.arrivalDate").gte(route.getArrivalDate()))
            );
            query.addCriteria(finalCriteria);
            List<BusRoutesEntity> routes = mongoTemplate.find(query, BusRoutesEntity.class);
            if(!routes.isEmpty()) throw new BusAlreadyInRouteException(busRegNo+" bus is busy for thr given time");
        });

    }

    public Void deleteRoute(DeleteRouteRequest request) {

        Query query = new Query(Criteria.where("busRegNo").is(request.getBusRegNo()));
        query.addCriteria(Criteria.where("routeInfo.departureDate").is(request.getDepartureDate()).andOperator(Criteria.where("routeInfo.arrivalDate").is(request.getArrivalDate())));
        log.info("removing the given route");
        mongoTemplate.remove(query, BusRoutesEntity.class);
        return null;
    }

    public List<RouteInfo> getBusesOnRoute(String origin, String destination, String departureDate) {

        log.info("checking for buses for the given origin-destination and departureDate-arrivalDate");
        List<String> stops = Arrays.asList(origin, destination);

        log.info("adding criteria for searching buses");
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(new Criteria().orOperator(Criteria.where("routeInfo.origin").is(origin), Criteria.where("routeInfo.stops").in(origin)));
        criteriaList.add(new Criteria().orOperator(Criteria.where("routeInfo.destination").is(destination), Criteria.where("routeInfo.stops").in(destination)));
        criteriaList.add(Criteria.where("routeInfo.departureDate").is(departureDate));
        criteriaList.add(Criteria.where("routeInfo.availableSeats").ne(0));
        Criteria criteria = new Criteria();
        Query query = new Query(criteria.andOperator(criteriaList));
        return mongoTemplate.find(query, BusRoutesEntity.class).stream().map(i-> i.getRouteInfo()).toList();
    }

    public List<RouteInfo> getRoutesByBusRegNo(String busRegNo) {

        log.info("fetching routes for the given busRegNo");
        List<BusRoutesEntity> routes = busRoutesRepo.findByBusRegNo(busRegNo);
        if(routes.isEmpty()) throw new RouteNotExistsException("No routes present for given bus: "+ busRegNo);
        return routes.stream().map(BusRoutesEntity::getRouteInfo).toList();
    }

    public String bookTicket(BookTicketRequest request) {

        Query query = new Query(Criteria.where("busRegNo").is(request.getBusRegNo()));
        query.addCriteria(Criteria.where("routeInfo.origin").is(request.getOrigin()));
        query.addCriteria(Criteria.where("routeInfo.departureDate").is(request.getDepartureDate()));
        query.addCriteria(Criteria.where("routeInfo.availableSeats").gte(request.getNoOfSeats()));
        log.info("query: {}", query);
        BusRoutesEntity busRoute = mongoTemplate.findOne(query, BusRoutesEntity.class);
        if(Objects.isNull(busRoute)) throw new RouteNotExistsException("Route not exists for the given details");

        // create a booking entity and add the history of travelling
        log.info("booking {} seats", request.getNoOfSeats());
        int seatsAvail = busRoute.getRouteInfo().getAvailableSeats()-request.getNoOfSeats();
        busRoute.getRouteInfo().setAvailableSeats(seatsAvail);
        busRoutesRepo.save(busRoute);

        return busRoute.getRouteInfo().getFares().get(request.getDestination());
    }
}
