package com.campuslands.proyecto_spring_boot_coworking.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Integer attendees;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal roomCost;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal servicesCost;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cancellationFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingItem> items = new ArrayList<>();

    public Booking() {
    }

    public Booking(LocalDateTime startTime, LocalDateTime endTime, Integer attendees, BookingStatus status, BigDecimal roomCost, BigDecimal servicesCost, BigDecimal totalCost, BigDecimal cancellationFee, Room room, Member member) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendees = attendees;
        this.status = status;
        this.roomCost = roomCost;
        this.servicesCost = servicesCost;
        this.totalCost = totalCost;
        this.cancellationFee = cancellationFee;
        this.room = room;
        this.member = member;
    }

    // Agrega un servicio a la reserva y mantiene ambas relaciones
    public void addItem(BookingItem item){
        items.add(item);
        item.setBooking(this);
    }

    // Elimina un servicio de la reserva y mantiene ambas relaciones
    public void removeItem(BookingItem item){
        items.remove(item);
        item.setBooking(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public BigDecimal getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(BigDecimal roomCost) {
        this.roomCost = roomCost;
    }

    public BigDecimal getServicesCost() {
        return servicesCost;
    }

    public void setServicesCost(BigDecimal servicesCost) {
        this.servicesCost = servicesCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(BigDecimal cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<BookingItem> getItems() {
        return items;
    }

    public void setItems(List<BookingItem> items) {
        this.items = items;
    }
}
