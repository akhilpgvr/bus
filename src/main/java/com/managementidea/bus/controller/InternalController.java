package com.managementidea.bus.controller;

import com.managementidea.bus.model.backOffice.RouteInfo;
import com.managementidea.bus.model.dtos.request.BookTicketRequest;
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
@RequestMapping("/internal")
public class InternalController {

    @Autowired
    private RouteService routeService;

    @Operation(summary = "Api for filtering bus for user", description = "")
    @GetMapping("/get-buses")
    public ResponseEntity<List<RouteInfo>> getBusesOnRoute(@RequestParam String origin
            , @RequestParam String destination, @RequestParam String departureDate) {

        log.info("fetching the info for origin: {}, destination: {}, departureDate: {}", origin, destination, departureDate);
        return new ResponseEntity<>(routeService.getBusesOnRoute(origin, destination, departureDate), HttpStatus.OK);
    }

    @Operation(summary = "Api for booking a Journey ticket", description = "")
    @PostMapping("/book-ticket")
    public ResponseEntity<String> bookTicket(@RequestBody BookTicketRequest request) {

        log.info("booking ticket");
        return new ResponseEntity<>(routeService.bookTicket(request), HttpStatus.OK);
    }
}
