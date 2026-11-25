package com.campuslands.proyecto_spring_boot_coworking.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookingItemRequestDTO {

    @NotNull(message = "El ID del servicio adicional es obligatorio")
    @Positive(message = "El ID del servicio debe ser positivo")
    private Long additionalServiceId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Integer quantity;

    public Long getAdditionalServiceId() {
        return additionalServiceId;
    }

    public void setAdditionalServiceId(Long additionalServiceId) {
        this.additionalServiceId = additionalServiceId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
