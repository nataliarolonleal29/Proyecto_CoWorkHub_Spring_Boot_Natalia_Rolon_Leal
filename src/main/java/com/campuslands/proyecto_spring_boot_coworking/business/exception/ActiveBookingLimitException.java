package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class ActiveBookingLimitException extends RuntimeException {
    public ActiveBookingLimitException(String message) {
        super(message);
    }
}
