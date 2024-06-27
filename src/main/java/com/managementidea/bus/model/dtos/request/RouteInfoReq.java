package com.managementidea.bus.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managementidea.bus.model.backOffice.EmployeeDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfoReq {

    private String origin;
    private String destination;
    private List<String> stops;   //<stops>
    private HashMap<String, String> fares;  //<destination, fare>
    private String departureDate;
    private String arrivalDate;
    @JsonIgnore
    private int availableSeats;
    private EmployeeDetails employeeDetails;
}
