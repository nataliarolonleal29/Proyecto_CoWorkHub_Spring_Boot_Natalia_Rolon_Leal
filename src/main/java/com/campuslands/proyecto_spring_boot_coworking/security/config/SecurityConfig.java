package com.campuslands.proyecto_spring_boot_coworking.security.config;

import com.campuslands.proyecto_spring_boot_coworking.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> auth
                // Rutas publicas
                .requestMatchers(
                        "/api/auth/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/doc/**",
                        "/error"
                ).permitAll()
                // Administración de catalogos: solo ADMIN
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/locations/**",
                        "/api/rooms/**",
                        "/api/equipment/**",
                        "/api/membership-plans/**",
                        "/api/additional-services/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/locations/**",
                        "/api/rooms/**",
                        "/api/equipment/**",
                        "/api/membership-plans/**",
                        "/api/additional-services/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/locations/**",
                        "/api/rooms/**",
                        "/api/equipment/**",
                        "/api/membership-plans/**",
                        "/api/additional-services/**"
                ).hasRole("ADMIN")
                // Administración de miembros
                .requestMatchers("/api/members/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                .requestMatchers("/api/user-accounts/**").hasRole("ADMIN")
                // El resto requiere autenticacion
                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
