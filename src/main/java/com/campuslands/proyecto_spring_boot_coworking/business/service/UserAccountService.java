package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {

    List<UserAccount> findAll();

    Optional<UserAccount> findById(Long id);

    Optional<UserAccount> findByUsername(String username);

    UserAccount save(UserAccount userAccount);

    void deleteById(Long id);

    boolean existsByUserName(String username);

}
