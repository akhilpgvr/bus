package com.managementidea.bus.controller;

import com.managementidea.bus.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/utility")
public class UtilityController { //This Controller can only use for testing purposes

    @Autowired
    private RouteService routeService;

    @GetMapping("/test")
    public LocalDateTime test(String dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
