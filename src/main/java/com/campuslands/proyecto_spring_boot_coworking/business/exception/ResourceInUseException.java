package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class ResourceInUseException extends RuntimeException {
    public ResourceInUseException(String message) {
        super(message);
    }
}
