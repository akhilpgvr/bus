package com.managementidea.bus.controller;

import com.managementidea.bus.model.request.AddBusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @PostMapping("/add-bus")
    public ResponseEntity<Void> addBus(@RequestParam String mobileNo, @RequestBody AddBusRequest request){

    }

}
