package com.managementidea.bus.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/route")
public class RoutesController {

    //getmaping for getting routes of a bus


    @PostMapping("/add-route")
    public ResponseEntity<Void> addRoute() { // no need to get mobile number -- user is already verified and bus data is got from busInfo-entity

        log.info("adding new route for {}");
    return null;
    }
}
