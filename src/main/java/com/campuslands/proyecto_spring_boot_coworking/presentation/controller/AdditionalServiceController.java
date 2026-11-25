package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.AdditionalServiceService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MembershipPlanService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.AdditionalService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.MembershipPlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/additional-services")
public class AdditionalServiceController {

    private final AdditionalServiceService additionalServiceService;

    public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping
    public ResponseEntity<List<AdditionalService>> findAll(){
        return ResponseEntity.ok(additionalServiceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalService> findById(@PathVariable Long id){
        AdditionalService additionalService = additionalServiceService.findById(id).orElseThrow(() -> new IllegalArgumentException("El servicio adicional no existe"));

        return ResponseEntity.ok(additionalService);
    }

    @PostMapping
    public ResponseEntity<AdditionalService> create(@RequestBody AdditionalService service){
        AdditionalService savedService = additionalServiceService.save(service);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedService);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AdditionalService> update(@PathVariable Long id, @RequestBody AdditionalService service){
        AdditionalService existing = additionalServiceService.findById(id).orElseThrow(() -> new IllegalArgumentException("El servicio adicional no existe"));

        existing.setName(service.getName());
        existing.setUnitPrice(service.getUnitPrice());

        AdditionalService updatedService = additionalServiceService.save(existing);

        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        additionalServiceService.findById(id).orElseThrow(() -> new IllegalArgumentException("El servicio adicional no existe"));

        additionalServiceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
