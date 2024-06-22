package com.managementidea.bus.model.entities;

import com.managementidea.bus.model.backOffice.EmployeeDetails;
import com.managementidea.bus.model.enums.BusTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bus-mgmt")
public class BusInfoEntity {

    @Id
    @Generated
    private String id;

    private String mobileNo;
    private String busRegNo;
    private String busRegId;
    private BusTypeEnum busType;
    private int Capacity;

    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
}
