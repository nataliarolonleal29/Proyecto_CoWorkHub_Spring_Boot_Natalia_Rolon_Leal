package com.campuslands.proyecto_spring_boot_coworking.security.service;

import com.campuslands.proyecto_spring_boot_coworking.business.service.UserAccountService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount userAccount = userAccountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario: " + username));

        return new CustomUserDetails(userAccount);
    }
}
