package com.campuslands.proyecto_spring_boot_coworking.security.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Role;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Long memberId;
    private final Role role;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserAccount userAccount) {

        this.username = userAccount.getUsername();
        this.password = userAccount.getPassword();
        this.role = userAccount.getRole();

        this.memberId = userAccount.getMember() != null ? userAccount.getMember().getId() : null;

        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public Long getMemberId() {
        return memberId;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
