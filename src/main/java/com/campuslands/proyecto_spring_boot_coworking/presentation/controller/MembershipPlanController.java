package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.EquipmentService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MembershipPlanService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Equipment;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.MembershipPlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership-plans")
public class MembershipPlanController {

    private final MembershipPlanService membershipPlanService;

    public MembershipPlanController(MembershipPlanService membershipPlanService) {
        this.membershipPlanService = membershipPlanService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipPlan>> findAll(){
        return ResponseEntity.ok(membershipPlanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipPlan> findById(@PathVariable Long id){
        MembershipPlan membershipPlan = membershipPlanService.findById(id).orElseThrow(() -> new IllegalArgumentException("El plan de membresía no existe"));

        return ResponseEntity.ok(membershipPlan);
    }

    @PostMapping
    public ResponseEntity<MembershipPlan> create(@RequestBody MembershipPlan plan){
        MembershipPlan savedPlan = membershipPlanService.save(plan);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlan);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipPlan> update(@PathVariable Long id, @RequestBody MembershipPlan plan){
        MembershipPlan existing = membershipPlanService.findById(id).orElseThrow(() -> new IllegalArgumentException("El plan de membresía no existe"));

        existing.setName(plan.getName());
        existing.setIncludedHoursPerMonth(plan.getIncludedHoursPerMonth());
        existing.setOverageDiscount(plan.getOverageDiscount());
        existing.setMaxActiveBookings(plan.getMaxActiveBookings());

        MembershipPlan updatedPlan = membershipPlanService.save(existing);

        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        membershipPlanService.findById(id).orElseThrow(() -> new IllegalArgumentException("El plan de membresía no existe"));

        membershipPlanService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
