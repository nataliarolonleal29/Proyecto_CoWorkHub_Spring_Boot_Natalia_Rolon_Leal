package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.LocationService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MemberService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.RoomService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final LocationService locationService;

    public RoomController(RoomService roomService, LocationService locationService) {
        this.roomService = roomService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> findAll(){
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long id){
        Room room = roomService.findById(id).orElseThrow(() -> new IllegalArgumentException("La sala no existe"));

        return ResponseEntity.ok(room);
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<Room>> findByLocation(@PathVariable Long locationId){
        Location location = locationService.findById(locationId).orElseThrow(() -> new IllegalArgumentException("La ubicación no existe"));
        List<Room> rooms = roomService.findByLocation(location);

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/location/{locationId}/active")
    public ResponseEntity<List<Room>> findActiveByLocation(@PathVariable Long locationId){
        Location location = locationService.findById(locationId).orElseThrow(() -> new IllegalArgumentException("La ubicación no existe"));

        List<Room> rooms = roomService.findActiveByLocation(location);

        return ResponseEntity.ok(rooms);
    }


    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room){
        Room savedRoom = roomService.save(room);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room room){
        Room existing = roomService.findById(id).orElseThrow(() -> new IllegalArgumentException("La sala no existe"));

        existing.setName(room.getName());
        existing.setType(room.getType());
        existing.setCapacity(room.getCapacity());
        existing.setHourlyRate(room.getHourlyRate());
        existing.setActive(room.isActive());
        existing.setLocation(room.getLocation());
        existing.setEquipment(room.getEquipment());

        Room updatedRoom = roomService.save(existing);

        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        roomService.findById(id).orElseThrow(() -> new IllegalArgumentException("La sala no existe"));

        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
