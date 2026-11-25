package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}
