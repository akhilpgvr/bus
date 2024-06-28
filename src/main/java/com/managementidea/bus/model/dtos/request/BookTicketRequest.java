package com.managementidea.bus.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTicketRequest {

    private String busRegNo;
    private String origin;
    private String destination;
    private String departureDate;
    private int noOfSeats;
}
