package com.campuslands.proyecto_spring_boot_coworking;

import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordCheckRunner implements CommandLineRunner {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public PasswordCheckRunner(
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {

        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void run(String... args) {

        var account = userAccountRepository.findByUsername("admin");

        if (account.isEmpty()) {
            System.out.println("PRUEBA ADMIN: CUENTA NO ENCONTRADA");
            return;
        }

        boolean matches = passwordEncoder.matches(
                "AdminDemo2026!",
                account.get().getPassword()
        );

        System.out.println("PRUEBA ADMIN: CONTRASEÑA CORRECTA = " + matches);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            "admin",
                            "AdminDemo2026!"
                    )
            );

            System.out.println("AUTH MANAGER: AUTENTICACIÓN CORRECTA");
            System.out.println("AUTH MANAGER: " + authentication.isAuthenticated());

        } catch (AuthenticationException exception) {
            System.out.println("AUTH MANAGER: AUTENTICACIÓN FALLIDA");
            System.out.println("ERROR: " + exception.getClass().getSimpleName());
            System.out.println("DETALLE: " + exception.getMessage());
        }
    }
}
