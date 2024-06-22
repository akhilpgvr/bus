package com.managementidea.bus.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managementidea.bus.model.backOffice.EmployeeDetails;
import com.managementidea.bus.model.backOffice.RouteInfo;
import com.managementidea.bus.model.enums.BusTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRequestDTO {

    @JsonIgnore
    private String mobileNo;
    private String busRegNo;
    private String busRegId;
    private BusTypeEnum busType;
    private String Capacity;
    private EmployeeDetails employeeDetails;
    private Set<RouteInfo> routeInfo;

}
