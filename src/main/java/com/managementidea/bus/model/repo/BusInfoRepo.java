package com.managementidea.bus.model.repo;

import com.managementidea.bus.model.entities.BusInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusInfoRepo extends MongoRepository<BusInfoEntity, String> {

    List<BusInfoEntity> findByMobileNo(String mobileNo);

    Optional<BusInfoEntity> findByBusRegNo(String busRegNo);
}
