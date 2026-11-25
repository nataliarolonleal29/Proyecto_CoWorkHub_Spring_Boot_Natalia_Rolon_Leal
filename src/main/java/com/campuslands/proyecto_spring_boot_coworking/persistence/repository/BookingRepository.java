package com.campuslands.proyecto_spring_boot_coworking.persistence.repository;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Booking;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByMemberId(Long memberId);

    boolean existsByRoomIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(Long roomId, Collection<BookingStatus> statuses, LocalDateTime endTime, LocalDateTime startTime);

    long countByMemberIdAndStatusInAndStartTimeAfter(Long memberId, Collection<BookingStatus> statuses, LocalDateTime now);

    long countByMemberIdAndStatusInAndStartTimeAfterAndIdNot(Long memberId, Collection<BookingStatus> statuses, LocalDateTime now, Long excludedBookingId);

    boolean existsByRoomIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(Long roomId, Collection<BookingStatus> statuses, LocalDateTime endTime, LocalDateTime startTime, Long excludedBookingId);

}
