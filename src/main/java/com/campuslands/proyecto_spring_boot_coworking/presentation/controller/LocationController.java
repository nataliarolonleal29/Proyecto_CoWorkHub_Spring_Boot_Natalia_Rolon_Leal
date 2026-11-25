package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.LocationService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> findAll(){
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable Long id){
        Location location = locationService.findById(id).orElseThrow(() -> new IllegalArgumentException("La ubicación no existe"));

        return ResponseEntity.ok(location);
    }

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody Location location){
        Location savedLocation = locationService.save(location);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocation);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable Long id, @RequestBody Location location){
        Location existing = locationService.findById(id).orElseThrow(() -> new IllegalArgumentException("La ubicación no existe"));

        existing.setName(location.getName());
        existing.setCity(location.getCity());
        existing.setAddress(location.getAddress());
        existing.setOpeningTime(location.getOpeningTime());
        existing.setClosingTime(location.getClosingTime());

        Location updatedLocation = locationService.save(existing);

        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        locationService.findById(id).orElseThrow(() -> new IllegalArgumentException("La ubicación no existe"));

        locationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
