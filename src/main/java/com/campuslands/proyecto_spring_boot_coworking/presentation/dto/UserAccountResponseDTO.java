package com.campuslands.proyecto_spring_boot_coworking.presentation.dto;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Role;

public class UserAccountResponseDTO {

    private Long id;
    private String username;
    private Role role;
    private Long memberId;

    public UserAccountResponseDTO() {
    }

    public UserAccountResponseDTO(Long id, String username, Role role, Long memberId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
