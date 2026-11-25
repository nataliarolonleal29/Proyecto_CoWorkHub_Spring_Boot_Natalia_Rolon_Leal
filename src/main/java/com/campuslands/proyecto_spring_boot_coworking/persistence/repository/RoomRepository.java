package com.campuslands.proyecto_spring_boot_coworking.persistence.repository;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByLocation(Location location);

    List<Room> findByLocationAndActiveTrue(Location location);

    boolean existsByNameAndLocation(String name, Location location);

    boolean existsByNameAndLocationAndIdNot(String name, Location location, Long id);

    boolean existsByIdAndBookingsIsNotEmpty(Long id);

}
