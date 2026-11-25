package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.LocationService;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return Optional.of(locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró la sede con ID: " + id)));
    }

    @Override
    @Transactional
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró la sede con ID: " + id);
        }

        locationRepository.deleteById(id);
    }
}
