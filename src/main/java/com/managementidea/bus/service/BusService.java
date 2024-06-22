package com.managementidea.bus.service;

import com.managementidea.bus.model.dtos.BusReqResDTO;
import com.managementidea.bus.model.dtos.reponse.FindByMobileResponse;
import com.managementidea.bus.model.entities.BusInfoEntity;
import com.managementidea.bus.model.enums.UserTypeEnum;
import com.managementidea.bus.model.repo.BusInfoRepo;
import com.managementidea.bus.service.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BusService {

    @Autowired
    private BusInfoRepo busInfoRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;


    public int getSeatCapacity(String busType) {

        log.info("checking capacity for {}", busType);
        return switch (busType) {
            case "FAST_PASSENGER" -> 50;
            case "SUPER_FAST" -> 55;
            case "LUXURY" -> 40;
            case "SLEEPER" -> 35;
            case "MINI_BUS" -> 20;
            default -> throw new IllegalArgumentException("bus type " + busType + " not found");
        };
    }

    public FindByMobileResponse getUserByMobileNoAndUserType(String mobileNo, UserTypeEnum userType) {

        log.info("Connecting with User service");
        return userClient.findByMobileNoAndUserType(mobileNo, userType).getBody();
    }

    public List<BusReqResDTO> getBusesByMobileNo(String mobileNo) {

        log.info("querying all buses with mobileNo: {}", mobileNo);
        List<BusInfoEntity> buses = busInfoRepo.findByMobileNo(mobileNo);
        return buses.stream().map(i-> modelMapper.map(i, BusReqResDTO.class)).toList();
    }

    public Void addNewBus(String mobileNo, List<BusReqResDTO> request) {

        log.info("check for the owner integrity");
        getUserByMobileNo(mobileNo);

    }

}
