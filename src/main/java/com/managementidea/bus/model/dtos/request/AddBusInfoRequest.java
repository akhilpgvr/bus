package com.managementidea.bus.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managementidea.bus.model.enums.BusTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBusInfoRequest {

    private String busRegNo;
    private String busRegId;
    private BusTypeEnum busType;
    @JsonIgnore
    private int capacity;

}
