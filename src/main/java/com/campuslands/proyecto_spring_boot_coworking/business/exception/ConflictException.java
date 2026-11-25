package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
