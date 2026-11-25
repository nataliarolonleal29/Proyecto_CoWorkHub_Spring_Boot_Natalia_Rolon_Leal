package com.campuslands.proyecto_spring_boot_coworking.business.exception;

public class MemberSuspendedException extends RuntimeException {
    public MemberSuspendedException(String message) {
        super(message);
    }
}
