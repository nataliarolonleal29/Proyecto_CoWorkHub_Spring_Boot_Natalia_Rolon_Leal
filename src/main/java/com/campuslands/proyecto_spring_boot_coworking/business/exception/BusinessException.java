package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
