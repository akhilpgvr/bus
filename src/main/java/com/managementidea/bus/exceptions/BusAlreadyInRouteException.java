package com.managementidea.bus.exceptions;

public class BusAlreadyInRouteException extends RuntimeException {

    public BusAlreadyInRouteException(String message) {
        super(message);
    }
}
