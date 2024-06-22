package com.managementidea.bus.model.dtos.reponse;

import com.managementidea.bus.model.enums.BusTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusInfoResponse {

    private String busRegNo;
    private String busRegId;
    private BusTypeEnum busType;
    private int capacity;
}
