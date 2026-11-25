package com.campuslands.proyecto_spring_boot_coworking.business.service;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Booking;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.MembershipPlan;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Booking> findAll();

    Optional<Booking> findById(Long id);

    List<Booking> findByMemberId(Long id);

    Booking create(Booking booking);

    Booking update(Long id, Booking booking);

    Booking cancel(Long id);

    Booking confirm(Long id);

    Booking complete(Long id);
}
