package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Equipment;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Room;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    List<Equipment> findAll();

    Optional<Equipment> findById(Long id);

    Equipment save(Equipment equipment);

    void deleteById(Long id);

}
