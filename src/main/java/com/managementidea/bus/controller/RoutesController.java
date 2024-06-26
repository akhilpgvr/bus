package com.managementidea.bus.controller;

import com.managementidea.bus.model.backOffice.RouteInfo;
import com.managementidea.bus.model.dtos.BusRouteDTO;
import com.managementidea.bus.model.dtos.request.DeleteRouteRequest;
import com.managementidea.bus.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/route")
public class RoutesController {

    @Autowired
    private RouteService routeService;

    @Operation(summary = "Api for adding routes os buses", description = "")
    @PostMapping("/add-route")
    public ResponseEntity<Void> addRoute(@RequestBody BusRouteDTO request) { // no need to get mobile number -- user is already verified and bus data is got from busInfo-entity

        log.info("adding new route for {}", request.getBusRegNo());
        return new ResponseEntity<>(routeService.addNewRoute(request), HttpStatus.OK);
    }

    @Operation(summary = "Api for filtering routes by busRegNo", description = "")
    @GetMapping("/routes-of-bus/{busRegNo}")
    public ResponseEntity<List<RouteInfo>> getRoutesByBusRegNo(@PathVariable(name = "busRegNo") String busRegNo) {

        log.info("Get routes by busRegNo for {}", busRegNo);
        return new ResponseEntity<>(routeService.getRoutesByBusRegNo(busRegNo), HttpStatus.OK);
    }

    @Operation(summary = "Api for delete routes by busRegNo", description = "")
    @DeleteMapping("/delete-route/{busRegNo}")
    public ResponseEntity<Void> deleteRoute(@PathVariable(name = "busRegNo") String busRegNo, @RequestBody DeleteRouteRequest request){

        log.info("removing route for given bus for given time");
        request.setBusRegNo(busRegNo);
        return new ResponseEntity<>(routeService.deleteRoute(request), HttpStatus.OK);
    }
}
