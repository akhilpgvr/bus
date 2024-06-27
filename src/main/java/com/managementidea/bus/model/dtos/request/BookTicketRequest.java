package com.managementidea.bus.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTicketRequest {

    private String busRegNo;
    private String origin;
    private String departureDate;
}
