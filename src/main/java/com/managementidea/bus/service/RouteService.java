package com.managementidea.bus.service;

import com.managementidea.bus.exceptions.BusAlreadyInRouteException;
import com.managementidea.bus.exceptions.BusInfoExistsException;
import com.managementidea.bus.model.backOffice.RouteInfo;
import com.managementidea.bus.model.dtos.BusRouteDTO;
import com.managementidea.bus.model.entities.BusRoutesEntity;
import com.managementidea.bus.model.repo.BusRoutesRepo;
import com.mongodb.client.DistinctIterable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RouteService {

    @Autowired
    private BusRoutesRepo busRoutesRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Void addNewRoute(BusRouteDTO request) {

        String regNo = request.getBusRegNo();
        request.getRouteInfo().forEach(route-> {

            log.info("Checking if the route is busy for given date");
            List<LocalDateTime> arrivalTimes = request.getRouteInfo().stream().map(RouteInfo::getArrivalDate).toList();
            checkForBusDuplicateRoutes(regNo, arrivalTimes);
        });

        BusRoutesEntity busRoute = new BusRoutesEntity();
        BeanUtils.copyProperties(request, busRoute);
        busRoutesRepo.save(busRoute);
        return null;
    }

    public void checkForBusDuplicateRoutes(String busRegNo, List<LocalDateTime> arrivalTimes) {

        log.info("checking for busRegNo");
        Criteria regNoCriteria = Criteria.where("busRegNo").is(busRegNo);

        arrivalTimes.forEach(arrivalTime-> {
            Query query = new Query();
            List<Criteria> criteriaList = new ArrayList<>();
            criteriaList.add(regNoCriteria);
            log.info("checking for arrivalDate");
            criteriaList.add(Criteria.where("routeInfo.departureDate").lte(arrivalTime)); // this means the bus is already in its trip
            Criteria finalCriteria = new Criteria();
            finalCriteria.andOperator(criteriaList);
            query.addCriteria(finalCriteria);
            List<BusRoutesEntity> routes = mongoTemplate.find(query, BusRoutesEntity.class);
            if(!routes.isEmpty()) throw new BusAlreadyInRouteException(busRegNo+" bus is busy for thr given time");
        });

    }
}
