package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ConflictException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.BookingService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.*;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.AdditionalServiceRepository;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.BookingRepository;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.MemberRepository;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.RoomRepository;
import com.campuslands.proyecto_spring_boot_coworking.security.CurrentUserService;
import com.campuslands.proyecto_spring_boot_coworking.security.service.CustomUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final Clock clock;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final CurrentUserService currentUserService;

    public BookingServiceImpl(BookingRepository bookingRepository, Clock clock, RoomRepository roomRepository, MemberRepository memberRepository, AdditionalServiceRepository additionalServiceRepository, CurrentUserService currentUserService) {
        this.bookingRepository = bookingRepository;
        this.clock = clock;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<Booking> findAll() {

        validateStaffAccess();

        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {

        Optional<Booking> booking = bookingRepository.findById(id);

        booking.ifPresent(this::validateBookingOwnership);

        return booking;
    }

    @Override
    public List<Booking> findByMemberId(Long memberId) {

        CustomUserDetails currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() == Role.MEMBER) {

            Long currentMemberId = getCurrentMemberId();

            if (!currentMemberId.equals(memberId)) {
                throw new AccessDeniedException("No puedes consultar las reservas de otro miembro");
            }
        }

        return bookingRepository.findByMemberId(memberId);
    }

    @Override
    @Transactional
    public Booking create(Booking booking) {
        if (booking == null){
            throw new IllegalArgumentException("La reserva no puede estar vacía");
        }

        CustomUserDetails currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() == Role.MEMBER) {

            Long memberId = getCurrentMemberId();

            Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("No se encontró el miembro asociado a la cuenta"));

            booking.setMember(member);
        }

        if (booking.getRoom() == null || booking.getRoom().getId() == null){
            throw new IllegalArgumentException("Debe seleccionar una sala válida");
        }

        if (booking.getMember() == null || booking.getMember().getId() == null){
            throw new IllegalArgumentException("Debe seleccionar un miembro válido");
        }

        // Validar duración
        validateDuration(booking.getStartTime(), booking.getEndTime());

        // Validar anticipación
        validateAdvenceNotice(booking.getStartTime());

        // Validar sala y capacidad
        Room room = validateRoom(booking.getRoom().getId(), booking.getAttendees());

        // Validar miembro
        Member member = validateMember(booking.getMember().getId());

        // Validar horario de funcionamiento
        validateOperatingHours(room, booking.getStartTime(), booking.getEndTime());

        // Validar que no exista otra reserva en ese horario
        validateNoOverlap(room.getId(), booking.getStartTime(), booking.getEndTime());

        // Validar límite de reservas activas
        validateMaxActiveBookings(member);

        // Calcular costo de la sala
        BigDecimal roomCost = calculateRoomCost(member, room, booking.getStartTime(), booking.getEndTime(), null);

        // Asignar las entidades obtenidas de la base de datos
        booking.setRoom(room);
        booking.setMember(member);

        // Preparar servicios adicionales
        prepareBookingItems(booking);

        // Establecer valores controlados por el sistema
        booking.setRoomCost(roomCost);
        booking.setStatus(BookingStatus.PENDING);
        booking.setCancellationFee(BigDecimal.ZERO);

        // Calcular servicios y costo total
        calculateTotalCost(booking);

        // Guardar la reserva
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking update(Long id, Booking booking) {
        // Buscar la reserva existente
        Booking existing = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("La reserva no existe"));
        validateBookingOwnership(existing);

        // Solo se pueden modificar reservas pendientes
        if (existing.getStatus() != BookingStatus.PENDING){
            throw new IllegalArgumentException("Solo se pueden modificar reservas pendientes");
        }

        if (booking == null){
            throw new IllegalArgumentException("Los datos de la reserva no pueden estar vacíos");
        }

        if (booking.getRoom() == null || booking.getRoom().getId() == null){
            throw new IllegalArgumentException("Debe seleccionar una sala válida");
        }

        // Validar duración y anticipación
        validateDuration(booking.getStartTime(), booking.getEndTime());
        validateAdvenceNotice(booking.getStartTime());

        // Validar la sala
        Room room = validateRoom(booking.getRoom().getId(), booking.getAttendees());

        // Conservar el miembro original de la reserva
        Member member = existing.getMember();

        // Validar horario de funcionamiento
        validateOperatingHours(room, booking.getStartTime(), booking.getEndTime());

        // Comprobar disponibilidad, excluyendo esta reserva
        validateNoOverLap(room.getId(), booking.getStartTime(), booking.getEndTime(), existing.getId());

        // Comprobar el límite de reservas del miembro
        validateMaxActiveBookings(member, existing.getId());

        // Calcular nuevamente el costo de la sala
        BigDecimal roomCost = calculateRoomCost(member, room, booking.getStartTime(), booking.getEndTime(), existing.getId());

        // Actualizar los datos principales
        existing.setRoom(room);
        existing.setStartTime(booking.getStartTime());
        existing.setEndTime(booking.getEndTime());
        existing.setAttendees(booking.getAttendees());
        existing.setRoomCost(roomCost);

        // Actualizar los servicios adicionales
        existing.getItems().clear();

        if (booking.getItems() != null){
            for (var item : booking.getItems()){
                existing.addItem(item);
            }
        }

        prepareBookingItems(existing);

        // Recalcular los costos
        calculateTotalCost(existing);

        // Guardar los cambios
        return bookingRepository.save(existing);
    }

    @Override
    @Transactional
    public Booking cancel(Long id) {

        // Buscar la reserva
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BusinessRuleException("La reserva no existe"));
        validateBookingOwnership(booking);

        // Verificar que pueda cancelarse
        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.CONFIRMED){
            throw new BusinessRuleException("Solo se pueden cancelar reservas pendientes o confirmadas");
        }

        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now(clock);

        // Calcular cuánto falta para iniciar
        Duration timeUntilStart = Duration.between(now, booking.getStartTime());

        // No permitir cancelar reservas iniciadas o a menos de 2 horas
        if (timeUntilStart.compareTo(Duration.ofHours(2)) < 0){
            throw new BusinessRuleException("No se puede cancelar una reserva con menos de 2 horas de anticipación");
        }

        // Calcular la penalización
        if (timeUntilStart.compareTo(Duration.ofHours(24)) >= 0){
            //Cancelación gratuita
            booking.setCancellationFee(BigDecimal.ZERO);
        } else {
            // Penalización del 50% del costo de la sala
            BigDecimal fee = booking.getRoomCost().multiply(new BigDecimal("0.50"));
            booking.setCancellationFee(fee.setScale(2, RoundingMode.HALF_UP));
        }

        // Cambiar el estado
        booking.setStatus(BookingStatus.CANCELLED);

        // Guardar los cambios
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking confirm(Long id) {
        // Buscar la reserva
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BusinessRuleException("La reserva no existe"));
        validateStaffAccess();

        // Verificar que esté pendiente
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BusinessRuleException("Solo se pueden confirmar reservas pendientes");
        }

        // Cambiar el estado
        booking.setStatus(BookingStatus.CONFIRMED);

        // Guardar los cambios
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking complete(Long id) {

        // Buscar la reserva
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BusinessRuleException("La reserva no existe"));
        validateStaffAccess();

        // Verificar que esté confirmada
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new BusinessRuleException("Solo se pueden completar reservas confirmadas");
        }

        // Verificar que la reserva haya terminado
        if (LocalDateTime.now(clock).isBefore(booking.getEndTime())) {
            throw new BusinessRuleException("No se puede completar una reserva que todavía no ha terminado");
        }

        // Cambiar el estado
        booking.setStatus(BookingStatus.COMPLETED);

        // Guardar los cambios
        return bookingRepository.save(booking);
    }

    private void validateDuration(LocalDateTime startTime, LocalDateTime endTime){
        if (startTime == null || endTime == null){
            throw new BusinessRuleException("La fecha de inicio y finalización son obligatorias");
        }

        if (!endTime.isAfter(startTime)){
            throw new BusinessRuleException("La fecha de finalización debe ser posterior a la fecha de inicio");
        }

        long minutes = Duration.between(startTime, endTime).toMinutes();

        if (minutes < 30 || minutes > 480 || minutes % 30 != 0){
            throw new BusinessRuleException("La duración debe estar entre 30 minutos y 8 horas, en intervalos de 30 minutos");
        }

        if (!startTime.toLocalDate().equals(endTime.toLocalDate())){
            throw new BusinessRuleException("La reserva debe comenzar y terminar el mismo día");
        }
    }

    private void validateAdvenceNotice(LocalDateTime startTime){

        LocalDateTime minimumStart = LocalDateTime.now(clock).plusHours(1);

        if(!startTime.isAfter(minimumStart) && !startTime.isEqual(minimumStart)){
            throw new BusinessRuleException("La reserva debe realizarse con al menos una hora de anticipación");
        }
    }

    private Room validateRoom(Long roomId, Integer attendees){

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No se encontró la sala con ID: " + roomId));

        if (!room.isActive()){
            throw new BusinessRuleException("No se puede reservar una sala inactiva");
        }

        if(attendees == null || attendees <= 0){
            throw new BusinessRuleException("Debe existir al menos un asistente");
        }

        if (attendees > room.getCapacity()){
            throw new BusinessRuleException("La cantidad de asistentes supera la capacidad de la sala");
        }

        return room;
    }

    private Member validateMember(Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("No se encontró el miembro con ID: " + memberId));

        if (member.getStatus() != MemberStatus.ACTIVE){
            throw new BusinessRuleException("El miembro no está activo y no puede realizar reservas");
        }

        return member;
    }

    private void validateOperatingHours(Room room, LocalDateTime startTime, LocalDateTime endTime){

        LocalTime openingTime = room.getLocation().getOpeningTime();
        LocalTime closingTime = room.getLocation().getClosingTime();

        LocalTime start = startTime.toLocalTime();
        LocalTime end = endTime.toLocalTime();

        if (start.isBefore(openingTime) || end.isAfter(closingTime)){
            throw new BusinessRuleException("La reserva debe respetar los horarios de funcionamiento de la sede");
        }
    }

    // Para create
    private void validateNoOverlap(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        boolean overlap = bookingRepository.existsByRoomIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(roomId, List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED), endTime, startTime);

        if (overlap) {
            throw new ConflictException("La sala ya tiene una reserva en ese horario");
        }
    }


    // Para update
    private void validateNoOverLap(Long roomId, LocalDateTime startTime, LocalDateTime endTime, Long excludedBookingId){

        boolean overLap = bookingRepository.existsByRoomIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(roomId, List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED), endTime, startTime, excludedBookingId);

        if (overLap){
            throw new IllegalArgumentException("La sala ya tiene una reserva en ese horario");
        }
    }

    private BigDecimal calculateRoomCost(Member member, Room room, LocalDateTime startTime, LocalDateTime endTime, Long excludedBookingId){

        MembershipPlan plan = member.getMembershipPlan();

        if (plan == null){
            throw new IllegalArgumentException("El miembro no tiene un plan de membresía asignado");
        }

        // Calcular las horas de la nueva reserva
        long requestedMinutes = Duration.between(startTime, endTime).toMinutes();

        BigDecimal requestedHours = BigDecimal.valueOf(requestedMinutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);


        // Identificar el mes de la reserva
        YearMonth reservationMonth = YearMonth.from(startTime);

        // Calcular las horas ya utilizadas en ese mes
        BigDecimal usedHours = BigDecimal.ZERO;

        List<Booking> memberBookings = bookingRepository.findByMemberId(member.getId());

        for (Booking existingBooking : memberBookings) {
            if (excludedBookingId != null && excludedBookingId.equals(existingBooking.getId())){
                continue;
            }
            if (existingBooking.getStatus() == BookingStatus.CANCELLED){
                continue;
            }

            YearMonth existingMonth = YearMonth.from(existingBooking.getStartTime());

            if (existingMonth.equals(reservationMonth)){
                long minutes = Duration.between(existingBooking.getStartTime(), existingBooking.getEndTime()).toMinutes();

                BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
                usedHours = usedHours.add(hours);
            }
        }

        // Calcular las horas gratuitas que todavía tiene disponibles
        BigDecimal includedHours = BigDecimal.valueOf(plan.getIncludedHoursPerMonth());

        BigDecimal remainingFreeHours = includedHours.subtract(usedHours).max(BigDecimal.ZERO);


        // Calcular las horas que deben cobrarse
        BigDecimal billableHours = requestedHours.subtract(remainingFreeHours).max(BigDecimal.ZERO);

        // Aplicar el descuento del plan
        BigDecimal discount = plan.getOverageDiscount().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discount);

        // Calcular el costo final de la sala
        return room.getHourlyRate().multiply(billableHours).multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateServicesCost(Booking booking){
        BigDecimal total = BigDecimal.ZERO;

        if (booking.getItems() == null || booking.getItems().isEmpty()){
            return total;
        }

        for (var item : booking.getItems()){
            if (item.getQuantity() <= 0){
                throw new IllegalArgumentException("La cantidad de servicios debe ser mayor que cero");
            }

            if (item.getAppliedUnitPrice() == null || item.getAppliedUnitPrice().compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("El precio aplicado del servicio no es válido");
            }

            BigDecimal subtotal = item.getAppliedUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(subtotal);

        }
        return total.setScale(2, RoundingMode.HALF_UP);

    }

    private void calculateTotalCost(Booking booking){

        BigDecimal roomCost = booking.getRoomCost() != null ? booking.getRoomCost() : BigDecimal.ZERO;

        BigDecimal servicesCost = calculateServicesCost(booking);

        booking.setServicesCost(servicesCost);

        booking.setTotalCost(roomCost.add(servicesCost).setScale(2, RoundingMode.HALF_UP));
    }

    // Para create
    private void validateMaxActiveBookings(Member member) {
        MembershipPlan plan = member.getMembershipPlan();

        if (plan == null) {
            throw new BusinessRuleException("El miembro no tiene un plan de membresía asignado");
        }

        long activeBookings = bookingRepository.countByMemberIdAndStatusInAndStartTimeAfter(member.getId(), List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED), LocalDateTime.now(clock));

        if (activeBookings >= plan.getMaxActiveBookings()) {
            throw new BusinessRuleException("El miembro ha alcanzado el límite de reservas activas permitido por su plan");
        }
    }


    // Para update
    private void validateMaxActiveBookings(Member member, Long excludedBookingId){

        // Obtener el plan de membresía del miembro
        MembershipPlan plan = member.getMembershipPlan();

        if (plan == null){
            throw new IllegalArgumentException("El miembro no tiene un plan de membresía asignado");
        }

        // Consultar cuántas reservas activas futuras tiene
        long activeBookings = bookingRepository.countByMemberIdAndStatusInAndStartTimeAfterAndIdNot(member.getId(), List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED), LocalDateTime.now(clock), excludedBookingId);

        // Comparar las reservas actuales con el límite del plan
        if (activeBookings >= plan.getMaxActiveBookings()){
            throw new IllegalArgumentException("El miembro ha alcanzado el límite de reservas activas permitido por su plan");
        }
    }

    private void prepareBookingItems(Booking booking){
        if (booking.getItems() == null){
            return;
        }

        for (var item : booking.getItems()){
            if (item.getQuantity() <= 0){
                throw new IllegalArgumentException("La cantidad del servicio debe ser mayor que cero");
            }

            if (item.getAdditionalService() == null || item.getAdditionalService().getId() == null){
                throw new IllegalArgumentException("Cada servicio debe tener un identificador válido");
            }

            //Buscar el servicio en la base de datos
            AdditionalService service = additionalServiceRepository.findById(item.getAdditionalService().getId()).orElseThrow(() -> new IllegalArgumentException("El servicio adicional no existe"));

            // Asignar el servicio real y su precio actual
            item.setAdditionalService(service);
            item.setAppliedUnitPrice(service.getUnitPrice());

            // Relacionar el detalle con la reserva
            item.setBooking(booking);
        }
    }

    private void validateStaffAccess() {

        CustomUserDetails currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() == Role.MEMBER) {
            throw new AccessDeniedException("No tienes permisos para realizar esta operación");
        }
    }

    private Long getCurrentMemberId() {

        CustomUserDetails currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.MEMBER || currentUser.getMemberId() == null) {
            throw new AccessDeniedException("La cuenta no está asociada a un miembro");
        }
        return currentUser.getMemberId();
    }

    private void validateBookingOwnership(Booking booking) {

        CustomUserDetails currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() == Role.MEMBER) {

            Long memberId = getCurrentMemberId();

            if (!memberId.equals(booking.getMember().getId())) {
                throw new AccessDeniedException("No puedes acceder a una reserva de otro miembro");
            }
        }
    }

}
