package com.campuslands.proyecto_spring_boot_coworking.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_items")
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal appliedUnitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "additional_service_id", nullable = false)
    private AdditionalService additionalService;

    public BookingItem() {
    }

    public BookingItem(Integer quantity, BigDecimal appliedUnitPrice, AdditionalService additionalService) {
        this.quantity = quantity;
        this.appliedUnitPrice = appliedUnitPrice;
        this.additionalService = additionalService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public AdditionalService getAdditionalService() {
        return additionalService;
    }

    public void setAdditionalService(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }
}
