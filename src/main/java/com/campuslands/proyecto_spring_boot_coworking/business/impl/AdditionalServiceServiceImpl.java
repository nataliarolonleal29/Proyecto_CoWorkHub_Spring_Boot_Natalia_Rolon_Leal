package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ConflictException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.AdditionalServiceService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.AdditionalService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.AdditionalServiceRepository;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.BookingItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdditionalServiceServiceImpl implements AdditionalServiceService {

    private final AdditionalServiceRepository additionalServiceRepository;
    private final BookingItemRepository bookingItemRepository;

    public AdditionalServiceServiceImpl(AdditionalServiceRepository additionalServiceRepository, BookingItemRepository bookingItemRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
        this.bookingItemRepository = bookingItemRepository;
    }

    @Override
    public List<AdditionalService> findAll() {
        return additionalServiceRepository.findAll();
    }

    @Override
    public Optional<AdditionalService> findById(Long id) {
        return Optional.of(additionalServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el servicio adicional con ID: " + id)));
    }

    @Override
    public Optional<AdditionalService> findByName(String name) {
        return additionalServiceRepository.findByName(name);
    }

    @Override
    @Transactional
    public AdditionalService save(AdditionalService additionalService) {

        additionalServiceRepository.findByName(additionalService.getName()).ifPresent(existing -> {
            if (!existing.getId().equals(additionalService.getId())) {
                throw new ConflictException("Ya existe un servicio adicional con ese nombre");
            }
        });

        return additionalServiceRepository.save(additionalService);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!additionalServiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el servicio adicional con ID: " + id);
        }

        if (bookingItemRepository.existsByAdditionalServiceId(id)) {
            throw new BusinessRuleException("No se puede eliminar un servicio que está asociado a reservas.");
        }

        additionalServiceRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return additionalServiceRepository.existsByName(name);
    }
}
