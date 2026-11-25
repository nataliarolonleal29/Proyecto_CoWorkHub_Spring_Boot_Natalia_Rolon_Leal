package com.campuslands.proyecto_spring_boot_coworking.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public class BookingRequestDTO {

    @NotNull(message = "El ID de la sala es obligatorio")
    @Positive(message = "El ID de la sala debe ser positivo")
    private Long roomId;

    @NotNull(message = "El ID del miembro es obligatorio")
    @Positive(message = "El ID del miembro debe ser positivo")
    private Long memberId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime startTime;

    @NotNull(message = "La fecha de finalización es obligatoria")
    private LocalDateTime endTime;

    @NotNull(message = "El número de asistentes es obligatorio")
    @Positive(message = "Debe existir al menos un asistente")
    private Integer attendees;

    @Valid
    private List<BookingItemRequestDTO> items;

    public BookingRequestDTO() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getAttendees() {
        return attendees;
    }

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }

    public List<BookingItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<BookingItemRequestDTO> items) {
        this.items = items;
    }
}
