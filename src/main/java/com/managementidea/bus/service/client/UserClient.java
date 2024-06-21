package com.managementidea.bus.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserManagementClient", url = "${config.rest.service.user-url}")
public interface UserClient {

    @GetMapping("/internal/find-by-mob/{mobileNo}")
    public ResponseEntity<Object> findByMobileNo(@PathVariable(name = "mobileNo") String mobileNo);
}
