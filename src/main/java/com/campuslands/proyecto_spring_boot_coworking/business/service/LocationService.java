package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<Location> findAll();

    Optional<Location> findById(Long id);

    Location save(Location location);

    void deleteById(Long id);

}
