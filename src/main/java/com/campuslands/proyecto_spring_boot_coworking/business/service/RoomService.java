package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> findAll();

    Optional<Room> findById(Long id);

    List<Room> findByLocation(Location location);

    List<Room> findActiveByLocation(Location location);

    Room save(Room room);

    void deleteById(Long id);


}
