package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.AdditionalService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;

import java.util.List;
import java.util.Optional;

public interface AdditionalServiceService {

    List<AdditionalService> findAll();

    Optional<AdditionalService> findById(Long id);

    Optional<AdditionalService> findByName(String name);

    AdditionalService save(AdditionalService additionalService);

    void deleteById(Long id);

    boolean existsByName(String name);

}
