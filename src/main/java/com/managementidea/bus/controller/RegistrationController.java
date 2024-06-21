package com.managementidea.bus.controller;

import com.managementidea.bus.service.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/test")
    public ResponseEntity<Object> busRegister(@RequestParam String mobileNo){
        return new ResponseEntity<>(userClient.findByMobileNo(mobileNo), HttpStatus.OK);
    }
}
