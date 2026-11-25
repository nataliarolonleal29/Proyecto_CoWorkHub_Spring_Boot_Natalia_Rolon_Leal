package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class CancellationNotAllowedException extends RuntimeException {
    public CancellationNotAllowedException(String message) {
        super(message);
    }
}
