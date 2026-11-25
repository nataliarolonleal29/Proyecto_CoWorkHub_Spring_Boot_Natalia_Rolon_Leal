package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.BookingService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.*;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.BookingItemRequestDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.BookingItemResponseDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.BookingRequestDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.BookingResponseDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.mapper.BookingMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> findAll(){

        List<BookingResponseDTO> bookings = bookingService.findAll().stream().map(bookingMapper::toDTO).toList();

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> findById(@PathVariable Long id){
        Booking booking = bookingService.findById(id).orElseThrow(() -> new IllegalArgumentException("La reserva no existe"));

        return ResponseEntity.ok(bookingMapper.toDTO(booking));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookingResponseDTO>> findByMemberId(@PathVariable Long memberId) {

        List<BookingResponseDTO> bookings = bookingService.findByMemberId(memberId).stream().map(bookingMapper::toDTO).toList();

        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> create(@Valid @RequestBody BookingRequestDTO request) {

        Booking booking = toEntity(request);

        Booking savedBooking = bookingService.create(booking);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingMapper.toDTO(savedBooking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequestDTO request) {

        Booking booking = toEntity(request);

        Booking updatedBooking = bookingService.update(id, booking);

        return ResponseEntity.ok(bookingMapper.toDTO(updatedBooking));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancel(
            @PathVariable Long id) {

        Booking cancelledBooking = bookingService.cancel(id);

        return ResponseEntity.ok(bookingMapper.toDTO(cancelledBooking));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<BookingResponseDTO> confirm(
            @PathVariable Long id) {

        Booking confirmedBooking = bookingService.confirm(id);

        return ResponseEntity.ok(bookingMapper.toDTO(confirmedBooking));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<BookingResponseDTO> complete(
            @PathVariable Long id) {

        Booking completedBooking = bookingService.complete(id);

        return ResponseEntity.ok(bookingMapper.toDTO(completedBooking));
    }

    private Booking toEntity(BookingRequestDTO request) {

        Booking booking = new Booking();

        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setAttendees(request.getAttendees());

        Room room = new Room();
        room.setId(request.getRoomId());
        booking.setRoom(room);

        if (request.getMemberId() != null) {
            Member member = new Member();
            member.setId(request.getMemberId());
            booking.setMember(member);
        }

        List<BookingItem> items = new ArrayList<>();

        if (request.getItems() != null) {

            for (BookingItemRequestDTO itemRequest : request.getItems()) {

                AdditionalService service = new AdditionalService();
                service.setId(itemRequest.getAdditionalServiceId());

                BookingItem item = new BookingItem();
                item.setQuantity(itemRequest.getQuantity());
                item.setAdditionalService(service);

                items.add(item);
            }
        }

        booking.setItems(items);

        return booking;
    }

}
