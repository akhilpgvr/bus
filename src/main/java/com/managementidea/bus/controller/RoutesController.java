package com.managementidea.bus.controller;

import com.managementidea.bus.model.dtos.BusRouteDTO;
import com.managementidea.bus.model.dtos.request.DeleteRouteRequest;
import com.managementidea.bus.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update-route/{busRegNo}")
    public ResponseEntity<String> updateRoute(){

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/delete-route/{busRegNo}")
    public ResponseEntity<Void> deleteRoute(@PathVariable(name = "busRegNo") String busRegNo, @RequestBody DeleteRouteRequest request){

        log.info("removing route for given bus for given time");
        request.setBusRegNo(busRegNo);
        return new ResponseEntity<>(routeService.deleteRoute(request), HttpStatus.OK);
    }
}
