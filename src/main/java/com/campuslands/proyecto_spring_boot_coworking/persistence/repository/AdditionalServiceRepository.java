package com.campuslands.proyecto_spring_boot_coworking.persistence.repository;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {

    Optional<AdditionalService> findByName(String name);

    boolean existsByName(String name);

}
