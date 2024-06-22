package com.managementidea.bus.service;

import com.managementidea.bus.exceptions.BusInfoExistsException;
import com.managementidea.bus.exceptions.IncorrectBusRegNoException;
import com.managementidea.bus.model.dtos.BusReqResDTO;
import com.managementidea.bus.model.dtos.reponse.FindByMobileResponse;
import com.managementidea.bus.model.entities.BusInfoEntity;
import com.managementidea.bus.model.enums.BusTypeEnum;
import com.managementidea.bus.model.enums.UserTypeEnum;
import com.managementidea.bus.model.repo.BusInfoRepo;
import com.managementidea.bus.service.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class BusService {

    @Autowired
    private BusInfoRepo busInfoRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;


    public BusInfoEntity findByBusRegNo(String busRegNo) {

        log.info("find by busRegNo: {}", busRegNo);
        Optional<BusInfoEntity> busInfo = busInfoRepo.findByBusRegNo(busRegNo);
        if(busInfo.isPresent()) return busInfo.get();
        else {
            log.error("Bus details not found for: {}", busRegNo);
            throw new IncorrectBusRegNoException("BusInfo not present for: "+busRegNo);
        }
    }
    public int getSeatCapacity(BusTypeEnum busType) {

        log.info("checking capacity for {}", busType);
        return switch (busType) {
            case FAST_PASSENGER -> 50;
            case SUPER_FAST -> 55;
            case LUXURY -> 40;
            case SLEEPER -> 35;
            case MINI_BUS -> 20;
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

    public Void addNewBus(String mobileNo, Set<BusReqResDTO> request) {

        log.info("check for the owner integrity");
        getUserByMobileNoAndUserType(mobileNo, UserTypeEnum.BUS_OWNER); // user must be owner

        log.info("Adding new bus");
        request.forEach(i-> {
            log.info("checking bus is already present");
            try{
                findByBusRegNo(i.getBusRegNo());
                log.error("Bus Already Registered");
                throw new BusInfoExistsException("Bus Already Registered");
            }
            catch (IncorrectBusRegNoException ex){

                BusInfoEntity busInfo = new BusInfoEntity();
                BeanUtils.copyProperties(i, busInfo);
                busInfo.setCapacity(getSeatCapacity(i.getBusType()));
                busInfo.setCreatedOn(LocalDateTime.now());
                busInfo.setLastUpdatedOn(LocalDateTime.now());
                busInfoRepo.save(busInfo);
            }
            catch (Exception ex){
                log.error("");
                throw ex;
            }
        });
        return null;
    }

}
