package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class BookingConflictException extends RuntimeException {
    public BookingConflictException(String message) {
        super(message);
    }
}
