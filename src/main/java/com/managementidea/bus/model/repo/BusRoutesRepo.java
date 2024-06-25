package com.managementidea.bus.model.repo;

import com.managementidea.bus.model.entities.BusRoutesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRoutesRepo extends MongoRepository<BusRoutesEntity, String> {

    Optional<BusRoutesEntity> findByBusRegNo(String busRegNo);
}
