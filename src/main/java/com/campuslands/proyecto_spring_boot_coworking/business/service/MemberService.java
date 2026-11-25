package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Booking;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.MembershipPlan;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    List<Member> findAll();

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByDocumentNumber(String documentNumber);

    Member save(Member member);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    boolean existsByDocumentNumber(String documentNumber);
}
