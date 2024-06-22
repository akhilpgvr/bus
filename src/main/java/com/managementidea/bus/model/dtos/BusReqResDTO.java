package com.managementidea.bus.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managementidea.bus.model.backOffice.EmployeeDetails;
import com.managementidea.bus.model.enums.BusTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusReqResDTO {

    private String busRegNo;
    private String busRegId;
    private BusTypeEnum busType;
    @JsonIgnore
    private int capacity;
    private EmployeeDetails employeeDetails;

}
