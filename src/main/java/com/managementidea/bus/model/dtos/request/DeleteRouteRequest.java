package com.managementidea.bus.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRouteRequest {

    @JsonIgnore
    private String busRegNo;
    private String departureDate;
    private String arrivalDate;
}
