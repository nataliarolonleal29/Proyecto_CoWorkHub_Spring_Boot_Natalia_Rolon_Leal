package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.EquipmentService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.LocationService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Equipment;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> findAll(){
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> findById(@PathVariable Long id){
        Equipment equipment = equipmentService.findById(id).orElseThrow(() -> new IllegalArgumentException("El equipo no existe"));

        return ResponseEntity.ok(equipment);
    }

    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipment){
        Equipment savedEquipment = equipmentService.save(equipment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(@PathVariable Long id, @RequestBody Equipment equipment){
        Equipment existing = equipmentService.findById(id).orElseThrow(() -> new IllegalArgumentException("El equipo no existe"));

        existing.setName(equipment.getName());

        Equipment updatedEquipment = equipmentService.save(existing);

        return ResponseEntity.ok(updatedEquipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        equipmentService.findById(id).orElseThrow(() -> new IllegalArgumentException("El equipo no existe"));

        equipmentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
