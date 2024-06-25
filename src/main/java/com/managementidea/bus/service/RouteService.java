package com.managementidea.bus.service;

import com.managementidea.bus.exceptions.BusAlreadyInRouteException;
import com.managementidea.bus.exceptions.RouteNotExistsException;
import com.managementidea.bus.model.backOffice.RouteInfo;
import com.managementidea.bus.model.dtos.BusRouteDTO;
import com.managementidea.bus.model.dtos.request.DeleteRouteRequest;
import com.managementidea.bus.model.entities.BusRoutesEntity;
import com.managementidea.bus.model.repo.BusRoutesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RouteService {

    @Autowired
    private BusRoutesRepo busRoutesRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BusService busService;

    public BusRoutesEntity findByBusRegNo(String busRegNo) {

        log.info("find by busRegNo: {}", busRegNo);
        Optional<BusRoutesEntity> busRoute = busRoutesRepo.findByBusRegNo(busRegNo);
        if(busRoute.isPresent()) return busRoute.get();
        else {
            log.error("Bus details not found for: {}", busRegNo);
            throw new RouteNotExistsException("BusRoute not present for: "+busRegNo);
        }
    }

    public Void addNewRoute(BusRouteDTO request) {

        String regNo = request.getBusRegNo();
        log.info("checking bus is present");
        busService.findByBusRegNo(regNo);

        Optional<BusRoutesEntity> busInfoRef = busRoutesRepo.findByBusRegNo(regNo);
        request.getRouteInfo().forEach(route-> {

            log.info("Checking if the route is busy for given date");
            checkForBusDuplicateRoutes(regNo, request.getRouteInfo());
        });

        if(busInfoRef.isPresent()){

            busInfoRef.get().getRouteInfo().addAll(request.getRouteInfo());
            busInfoRef.get().setLastUpdatedOn(LocalDateTime.now());
            busRoutesRepo.save(busInfoRef.get());
        }else {
            BusRoutesEntity busRoute = new BusRoutesEntity();
            BeanUtils.copyProperties(request, busRoute);
            busRoute.setCreatedOn(LocalDateTime.now());
            busRoute.setLastUpdatedOn(LocalDateTime.now());
            busRoutesRepo.save(busRoute);
        }
        return null;
    }

    public void checkForBusDuplicateRoutes(String busRegNo, Set<RouteInfo> routeInfo) {

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
        BusRoutesEntity busRoutes = mongoTemplate.findOne(query, BusRoutesEntity.class);
        if(Objects.nonNull(busRoutes)) {
            log.info("checking for route is present");
            Optional<RouteInfo> route = busRoutes.getRouteInfo().stream().filter(i-> i.getDepartureDate().equalsIgnoreCase(request.getDepartureDate()) && i.getArrivalDate().equalsIgnoreCase(request.getArrivalDate())).findFirst();
            if(route.isPresent()) {
                log.info("removing the route");
                busRoutes.getRouteInfo().remove(route.get());
                busRoutesRepo.save(busRoutes);
            }
        }
        return null;
    }
}
