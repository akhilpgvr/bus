package com.managementidea.bus.model.backOffice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfo {

    private String origin;
    private String destination;
    private Set<String> stops;   //<stops>
    private HashMap<String, String> fares;  //<destination, fare>
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private EmployeeDetails employeeDetails;
}
