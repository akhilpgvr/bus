package com.managementidea.bus.model.dtos;

import com.managementidea.bus.model.backOffice.EmployeeDetails;
import com.managementidea.bus.model.backOffice.RouteInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRouteDTO {

    private String busRegNo;
    private Set<RouteInfo> routeInfo;
    private EmployeeDetails employeeDetails;
}
