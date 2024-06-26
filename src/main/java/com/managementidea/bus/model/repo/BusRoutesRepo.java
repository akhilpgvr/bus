package com.managementidea.bus.model.repo;

import com.managementidea.bus.model.entities.BusRoutesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRoutesRepo extends MongoRepository<BusRoutesEntity, String> {

}
