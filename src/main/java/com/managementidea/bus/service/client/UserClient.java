package com.managementidea.bus.service.client;

import com.managementidea.bus.model.dtos.reponse.FindByMobileResponse;
import com.managementidea.bus.model.enums.UserTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "UserManagementClient", url = "${config.rest.service.user-url}")
public interface UserClient {

    @GetMapping("find-by/mobnusertype/{mobileNo}")
    ResponseEntity<FindByMobileResponse> findByMobileNoAndUserType(@PathVariable(name = "mobileNo") String mobileNo, @RequestParam UserTypeEnum userType);
}
