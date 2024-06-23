package com.managementidea.bus.controller;

import com.managementidea.bus.model.entities.BusRoutesEntity;
import com.managementidea.bus.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/utility")
public class UtilityController { //This Controller can only use for testing purposes

    @Autowired
    private RouteService routeService;

    @GetMapping("/test")
    public void test(@RequestParam String busRegNo, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<LocalDateTime> arrivalTimes) {
        routeService.checkForBusDuplicateRoutes(busRegNo, arrivalTimes);
    }
}
