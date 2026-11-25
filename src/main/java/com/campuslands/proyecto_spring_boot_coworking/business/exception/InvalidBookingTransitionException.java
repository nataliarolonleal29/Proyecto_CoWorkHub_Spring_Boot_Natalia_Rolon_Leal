package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class InvalidBookingTransitionException extends RuntimeException {
    public InvalidBookingTransitionException(String message) {
        super(message);
    }
}
