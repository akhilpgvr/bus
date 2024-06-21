package com.managementidea.bus.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRegRequest {

    private String mobileNo;

    private String x;
}
