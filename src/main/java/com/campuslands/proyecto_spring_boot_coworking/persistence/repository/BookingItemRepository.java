package com.campuslands.proyecto_spring_boot_coworking.persistence.repository;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {

    boolean existsByAdditionalServiceId(Long additionalServiceId);

}
