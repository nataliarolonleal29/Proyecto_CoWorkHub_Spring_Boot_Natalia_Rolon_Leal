package com.campuslands.proyecto_spring_boot_coworking.security;

import com.campuslands.proyecto_spring_boot_coworking.security.service.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


@Service
public class CurrentUserService {

    public CustomUserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)){
            throw new AccessDeniedException("Usuario no autenticado");
        }

        return (CustomUserDetails) authentication.getPrincipal();

    }














}
