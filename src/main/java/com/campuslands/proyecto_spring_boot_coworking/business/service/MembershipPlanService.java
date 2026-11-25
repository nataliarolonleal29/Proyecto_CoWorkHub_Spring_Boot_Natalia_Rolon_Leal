package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Equipment;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.MembershipPlan;

import java.util.List;
import java.util.Optional;

public interface MembershipPlanService {

    List<MembershipPlan> findAll();

    Optional<MembershipPlan> findById(Long id);

    MembershipPlan save(MembershipPlan membershipPlan);

    void deleteById(Long id);

}
