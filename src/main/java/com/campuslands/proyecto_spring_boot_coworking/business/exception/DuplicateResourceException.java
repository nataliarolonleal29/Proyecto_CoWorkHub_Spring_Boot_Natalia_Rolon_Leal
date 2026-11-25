package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
