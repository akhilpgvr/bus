package com.managementidea.bus.model.dtos.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindByMobileResponse {

    private String userName;
    private PersonnelInfo userInfo;

}
