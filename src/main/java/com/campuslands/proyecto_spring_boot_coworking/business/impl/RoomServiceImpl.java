package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ConflictException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.RoomService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Room;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.LocationRepository;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> findByLocation(Location location) {

        if (location == null || location.getId() == null) {
            throw new ResourceNotFoundException("La sede indicada no existe");
        }

        return roomRepository.findByLocation(location);
    }

    @Override
    public List<Room> findActiveByLocation(Location location) {

        if (location == null || location.getId() == null) {
            throw new ResourceNotFoundException("La sede indicada no existe");
        }

        return roomRepository.findByLocationAndActiveTrue(location);
    }

    @Override
    @Transactional
    public Room save(Room room) {

        if (room.getLocation() == null || room.getLocation().getId() == null) {
            throw new ResourceNotFoundException("La sede de la sala es obligatoria");
        }

        boolean exists;

        if (room.getId() == null) {
            exists = roomRepository.existsByNameAndLocation(room.getName(), room.getLocation());
        } else {
            exists = roomRepository.existsByNameAndLocationAndIdNot(room.getName(), room.getLocation(), room.getId());
        }

        if (exists) {
            throw new ConflictException("Ya existe una sala con ese nombre en esta sede");
        }

        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró la sala con ID: " + id);
        }

        if (roomRepository.existsByIdAndBookingsIsNotEmpty(id)) {
            throw new BusinessRuleException("No se puede eliminar la sala porque tiene reservas asociadas");
        }

        roomRepository.deleteById(id);
    }



}
