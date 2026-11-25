package com.campuslands.proyecto_spring_boot_coworking.presentation.mapper;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Booking;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.BookingItem;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.BookingItemResponseDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.BookingResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BookingMapper {

    public BookingResponseDTO toDTO(Booking booking){
        BookingResponseDTO dto = new BookingResponseDTO();

        dto.setId(booking.getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setAttendees(booking.getAttendees());
        dto.setStatus(booking.getStatus());

        dto.setRoomId(booking.getRoom().getId());
        dto.setRoomName(booking.getRoom().getName());

        dto.setMemberId(booking.getMember().getId());
        dto.setMemberName(booking.getMember().getFullName());

        dto.setRoomCost(booking.getRoomCost());
        dto.setServicesCost(booking.getServicesCost());
        dto.setTotalCost(booking.getTotalCost());
        dto.setCancellationFee(booking.getCancellationFee());

        List<BookingItemResponseDTO> items = booking.getItems().stream().map(this::itemToDTO).toList();

        dto.setItems(items);

        return dto;

    }

    private BookingItemResponseDTO itemToDTO(BookingItem item){
        BigDecimal subtotal = item.getAppliedUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        return new BookingItemResponseDTO(item.getId(), item.getAdditionalService().getId(), item.getAdditionalService().getName(), item.getQuantity(), item.getAppliedUnitPrice(), subtotal);

    }





}
