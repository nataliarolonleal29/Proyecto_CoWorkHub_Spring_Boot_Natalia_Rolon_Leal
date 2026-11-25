package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.EquipmentService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Equipment;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Override
    public Optional<Equipment> findById(Long id) {
        return Optional.of(equipmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el equipo con ID: " + id)));
    }

    @Override
    @Transactional
    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!equipmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el equipo con ID: " + id);
        }

        if (equipmentRepository.existsByIdAndRoomsIsNotEmpty(id)) {
            throw new BusinessRuleException("No se puede eliminar el equipo porque está asociado a una o más salas");
        }

        equipmentRepository.deleteById(id);
    }
}
