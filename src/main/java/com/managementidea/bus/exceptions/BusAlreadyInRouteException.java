package com.managementidea.bus.exceptions;

import com.managementidea.bus.model.entities.BusRoutesEntity;

public class BusAlreadyInRouteException extends RuntimeException {

    public BusAlreadyInRouteException(String message) {
        super(message);
    }
}
