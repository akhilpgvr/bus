package com.managementidea.bus.model.entities;

import com.managementidea.bus.model.backOffice.RouteInfo;
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
@Document(collection = "bus-routes")
public class BusRoutesEntity {

    @Id
    @Generated
    private String id;

    private String busRegNo;
    private RouteInfo routeInfo;

    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
}
