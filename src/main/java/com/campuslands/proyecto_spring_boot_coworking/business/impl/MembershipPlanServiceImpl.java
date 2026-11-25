package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MembershipPlanService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.MembershipPlan;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;

    public MembershipPlanServiceImpl(MembershipPlanRepository membershipPlanRepository) {
        this.membershipPlanRepository = membershipPlanRepository;
    }

    @Override
    public List<MembershipPlan> findAll() {
        return membershipPlanRepository.findAll();
    }

    @Override
    public Optional<MembershipPlan> findById(Long id) {
        return Optional.of(membershipPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el plan de membresía con ID: " + id)));
    }

    @Override
    @Transactional
    public MembershipPlan save(MembershipPlan membershipPlan) {
        return membershipPlanRepository.save(membershipPlan);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!membershipPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el plan con ID: " + id);
        }

        if (membershipPlanRepository.existsByIdAndMembersIsNotEmpty(id)) {
            throw new BusinessRuleException("No se puede eliminar el plan porque tiene miembros asociados");
        }

        membershipPlanRepository.deleteById(id);
    }

}
