package com.managementidea.bus.controller;

import com.managementidea.bus.model.dtos.BusRouteDTO;
import com.managementidea.bus.model.entities.BusRoutesEntity;
import com.managementidea.bus.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/route")
public class RoutesController {

    @Autowired
    private RouteService routeService;

    //getmapping for getting routes of a bus using regNo


    @PostMapping("/add-route")
    public ResponseEntity<Void> addRoute(@RequestBody BusRouteDTO request) { // no need to get mobile number -- user is already verified and bus data is got from busInfo-entity

        log.info("adding new route for {}", request.getBusRegNo());
        return new ResponseEntity<>(routeService.addNewRoute(request), HttpStatus.OK);
    }
}
