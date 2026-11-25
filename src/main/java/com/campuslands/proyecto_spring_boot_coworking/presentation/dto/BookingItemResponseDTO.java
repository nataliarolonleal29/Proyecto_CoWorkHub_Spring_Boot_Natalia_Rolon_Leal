package com.campuslands.proyecto_spring_boot_coworking.presentation.dto;

import java.math.BigDecimal;

public class BookingItemResponseDTO {

    private Long id;
    private Long additionalServiceId;
    private String additionalServiceName;
    private Integer quantity;
    private BigDecimal appliedUnitPrice;
    private BigDecimal subtotal;

    public BookingItemResponseDTO() {
    }

    public BookingItemResponseDTO(Long id, Long additionalServiceId, String additionalServiceName, Integer quantity, BigDecimal appliedUnitPrice, BigDecimal subtotal) {
        this.id = id;
        this.additionalServiceId = additionalServiceId;
        this.additionalServiceName = additionalServiceName;
        this.quantity = quantity;
        this.appliedUnitPrice = appliedUnitPrice;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdditionalServiceId() {
        return additionalServiceId;
    }

    public void setAdditionalServiceId(Long additionalServiceId) {
        this.additionalServiceId = additionalServiceId;
    }

    public String getAdditionalServiceName() {
        return additionalServiceName;
    }

    public void setAdditionalServiceName(String additionalServiceName) {
        this.additionalServiceName = additionalServiceName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAppliedUnitPrice() {
        return appliedUnitPrice;
    }

    public void setAppliedUnitPrice(BigDecimal appliedUnitPrice) {
        this.appliedUnitPrice = appliedUnitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
