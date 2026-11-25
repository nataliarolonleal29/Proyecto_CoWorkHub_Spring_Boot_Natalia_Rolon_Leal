package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
