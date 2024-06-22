package com.managementidea.bus.controller;

import com.managementidea.bus.model.dtos.BusReqResDTO;
import com.managementidea.bus.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/register")
public class BusController {

    @Autowired
    private BusService busService;


    @Operation(summary = "Api to get bus list of owner", description = "")
    @GetMapping("/by-mobileNo")
    public ResponseEntity<List<BusReqResDTO>> getBusInfoByMobileNo (@RequestParam String mobileNo) {

        log.info("find buses of owner: {}", mobileNo);
        return new ResponseEntity<>(busService.getBusesByMobileNo(mobileNo), HttpStatus.OK);
    }

    @Operation(summary = "APi for adding new bus details", description = "")
    @PostMapping("/add-bus")
    public ResponseEntity<Void> addBus(@RequestParam String mobileNo, @RequestBody Set<BusReqResDTO> request){

        log.info("add a new bus details for mobileNo: {}", mobileNo);
        return new ResponseEntity<>(busService.addNewBus(mobileNo, request), HttpStatus.OK);
    }

}
