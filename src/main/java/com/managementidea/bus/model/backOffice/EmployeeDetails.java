package com.managementidea.bus.model.backOffice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetails {
    //driver
    private String driverName;
    private String licenseNo;
    private String driverMobNo;
    //conductor
    private String conductorName;
    private String conductorMobNo;
}
