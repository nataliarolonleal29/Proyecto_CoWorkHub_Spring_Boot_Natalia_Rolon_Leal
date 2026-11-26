-- Equipos

INSERT INTO equipment(id, name) VALUES(1, 'Proyector');
INSERT INTO equipment(id, name) VALUES(2, 'Pizarra digital');
INSERT INTO equipment(id, name) VALUES(3, 'Pantalla');
INSERT INTO equipment(id, name) VALUES(4, 'Videoconferencia');


-- Servicios adicionales
INSERT INTO additional_services(id, name, unit_price) VALUES(1, 'Cafetería', 8000.00);
INSERT INTO additional_services(id, name, unit_price) VALUES(2, 'Impresiones', 500.00);
INSERT INTO additional_services(id, name, unit_price) VALUES(3, 'Refrigerios', 12000.00);


-- Planes de membresía
INSERT INTO membership_plans(id, name, included_hours_per_month, overage_discount, max_active_bookings) VALUES(1, 'Básico', 10, 0.00, 2);
INSERT INTO membership_plans(id, name, included_hours_per_month, overage_discount, max_active_bookings) VALUES(2, 'Profesional', 25, 10.00, 4);
INSERT INTO membership_plans(id, name, included_hours_per_month, overage_discount, max_active_bookings) VALUES(3, 'Premium', 50, 20.00, 8);


-- Ubicaciones
INSERT INTO locations(id, name, city, address, opening_time, closing_time) VALUES(1, 'Sede Centro', 'Bucaramanga', 'Carrera 20 #36-47', '07:00:00', '21:00:00');
INSERT INTO locations(id, name, city, address, opening_time, closing_time) VALUES(2, 'Sede Cabecera', 'Bucaramanga', 'Carrera 33 #48-65', '08:00:00', '20:00:00');

-- Habitaciones
INSERT INTO rooms(id, name, type, capacity, hourly_rate, active, location_id) VALUES(1, 'Sala Ejecutiva', 'MEETING_ROOM', 12, 80000.00, TRUE, 1);
INSERT INTO rooms(id, name, type, capacity, hourly_rate, active, location_id) VALUES(2, 'Sala Creativa', 'MEETING_ROOM', 8, 60000.00, TRUE, 1);
INSERT INTO rooms(id, name, type, capacity, hourly_rate, active, location_id) VALUES(3, 'Oficina Privada A', 'PRIVATE_OFFICE', 12, 80000.00, TRUE, 1);
INSERT INTO rooms(id, name, type, capacity, hourly_rate, active, location_id) VALUES(4, 'Escritorio Compartido', 'DESK', 1, 15000.00, TRUE, 2);
INSERT INTO rooms(id, name, type, capacity, hourly_rate, active, location_id) VALUES(5, 'Sala Innovacion', 'MEETING_ROOM', 15, 100000.00, TRUE, 2);
INSERT INTO rooms(id, name, type, capacity, hourly_rate, active, location_id) VALUES(6, 'Oficina Privada B', 'PRIVATE_OFFICE', 5, 55000.00, FALSE, 2);


-- Relacion habitaciones - equipos
INSERT INTO room_equipment (room_id, equipment_id) VALUES (1, 1);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (1, 2);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (1, 4);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (2, 2);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (2, 3);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (3, 3);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (5, 1);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (5, 2);
INSERT INTO room_equipment (room_id, equipment_id) VALUES (5, 4);


-- Miembros
INSERT INTO members (id, document_number, full_name, email, status, membership_plan_id) VALUES (1, '1001001001', 'Laura Martinez', 'laura.martinez@example.com', 'ACTIVE', 1);
INSERT INTO members (id, document_number, full_name, email, status, membership_plan_id) VALUES (2, '1001001002', 'Carlos Rodriguez', 'carlos.rodriguez@example.com', 'ACTIVE', 2);
INSERT INTO members (id, document_number, full_name, email, status, membership_plan_id) VALUES (3, '1001001003', 'Sofia Hernandez', 'sofia.hernandez@example.com', 'ACTIVE', 3);
INSERT INTO members (id, document_number, full_name, email, status, membership_plan_id) VALUES (4, '1001001004', 'Andres Gomez', 'andres.gomez@example.com', 'ACTIVE', 1);
INSERT INTO members (id, document_number, full_name, email, status, membership_plan_id) VALUES (5, '1001001005', 'Daniela Torres', 'daniela.torres@example.com', 'SUSPENDED', 2);


-- ADMIN: $2a$10$WHxEeA3K//JM4kNpifwokeh6zGWUG2aNs.ewy.Kjah.6lEUA4YpHu
-- RECEPTIONIST: $2a$10$fQBnidCmD1SdS3bP7oOD..obzw5RxwbrYVfAkdrApCojVfhjWwXSC
-- MEMBER 1: $2a$10$u73zJyxeH8jF9Qe2yx2c1OdEj1NoT5SUKDmzUpGRA7gbYFdOOmcz6
-- MEMBER 2: $2a$10$oKxXPdpJ.JFEcbZYowNtDOVefjUkGivUoQ6lDcgjudrppCijqOu1C
-- Hash Laura:
-- $2a$10$9dEc3AqZPQIMsk.747mJgeK1/g.beydXjQeLuxIwLH4UNT7g5vslS
-- Hash Carlos:
-- $2a$10$YUTswbKb86qiHSKl.vDCru6kBCl6x5lBBSH2q3EauEAjzrP.PfPi2

-- Cuentas de usuario
INSERT INTO user_accounts (id, username, password, role, member_id) VALUES (1, 'admin', '$2a$10$WHxEeA3K//JM4kNpifwokeh6zGWUG2aNs.ewy.Kjah.6lEUA4YpHu', 'ADMIN', NULL);
INSERT INTO user_accounts (id, username, password, role, member_id) VALUES (2, 'recepcion', '$2a$10$fQBnidCmD1SdS3bP7oOD..obzw5RxwbrYVfAkdrApCojVfhjWwXSC', 'RECEPTIONIST', NULL);
INSERT INTO user_accounts (id, username, password, role, member_id) VALUES (3, 'laura', '$2a$10$9dEc3AqZPQIMsk.747mJgeK1/g.beydXjQeLuxIwLH4UNT7g5vslS', 'MEMBER', 1);
INSERT INTO user_accounts (id, username, password, role, member_id) VALUES (4, 'carlos', '$2a$10$YUTswbKb86qiHSKl.vDCru6kBCl6x5lBBSH2q3EauEAjzrP.PfPi2', 'MEMBER', 2);


-- Reservas

-- Reservas anteriores: COMPLETED y CANCELLED

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (1, '2026-11-10 09:00:00', '2026-11-10 11:00:00', 6, 'COMPLETED', 160000.00, 8000.00, 168000.00, 0.00, 1, 1);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (2, '2026-11-10 10:00:00', '2026-11-10 12:00:00', 4, 'COMPLETED', 120000.00, 5000.00, 125000.00, 0.00, 2, 2);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (3, '2026-11-11 09:00:00', '2026-11-11 12:00:00', 3, 'COMPLETED', 150000.00, 12000.00, 162000.00, 0.00, 3, 3);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (4, '2026-11-11 14:00:00', '2026-11-11 16:00:00', 1, 'COMPLETED', 30000.00, 0.00, 30000.00, 0.00, 4, 4);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (5, '2026-11-12 08:00:00', '2026-11-12 10:00:00', 10, 'COMPLETED', 200000.00, 12000.00, 212000.00, 0.00, 5, 1);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (6, '2026-11-13 09:00:00', '2026-11-13 10:00:00', 5, 'CANCELLED', 80000.00, 0.00, 80000.00, 0.00, 1, 2);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (7, '2026-11-13 14:00:00', '2026-11-13 16:00:00', 4, 'CANCELLED', 120000.00, 0.00, 120000.00, 60000.00, 2, 3);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (8, '2026-11-14 09:00:00', '2026-11-14 11:00:00', 3, 'CANCELLED', 100000.00, 0.00, 100000.00, 50000.00, 3, 4);


-- Reservas futuras: PENDING y CONFIRMED

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (9, '2026-11-20 09:00:00', '2026-11-20 11:00:00', 6, 'CONFIRMED', 160000.00, 8000.00, 168000.00, 0.00, 1, 1);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (10, '2026-11-20 10:00:00', '2026-11-20 11:00:00', 5, 'PENDING', 60000.00, 5000.00, 65000.00, 0.00, 2, 2);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (11, '2026-11-21 13:00:00', '2026-11-21 15:00:00', 3, 'CONFIRMED', 100000.00, 12000.00, 112000.00, 0.00, 3, 3);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (12, '2026-11-21 15:00:00', '2026-11-21 17:00:00', 1, 'PENDING', 30000.00, 0.00, 30000.00, 0.00, 4, 4);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (13, '2026-11-22 09:00:00', '2026-11-22 12:00:00', 12, 'CONFIRMED', 300000.00, 20000.00, 320000.00, 0.00, 5, 1);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (14, '2026-11-23 10:00:00', '2026-11-23 12:00:00', 5, 'PENDING', 160000.00, 0.00, 160000.00, 0.00, 1, 2);

INSERT INTO bookings (id, start_time, end_time, attendees, status, room_cost, services_cost, total_cost, cancellation_fee, room_id, member_id) VALUES (15, '2026-11-24 14:00:00', '2026-11-24 16:00:00', 6, 'CONFIRMED', 120000.00, 0.00, 120000.00, 0.00, 2, 3);


-- Servicios de las reservas

INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (1, 1, 1, 1, 8000.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (2, 2, 2, 10, 500.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (3, 3, 3, 1, 12000.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (4, 5, 3, 1, 12000.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (5, 9, 1, 1, 8000.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (6, 10, 2, 10, 500.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (7, 11, 3, 1, 12000.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (8, 13, 1, 1, 8000.00);
INSERT INTO booking_items (id, booking_id, additional_service_id, quantity, applied_unit_price) VALUES (9, 13, 3, 1, 12000.00);

-- user: "admin"
-- password: "AdminDemo2026!"
-- response 200 OK:
--{
--"accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc5NTYyNDQ4MCwiZXhwIjoxNzk1NzEwODgwfQ.hWI6o_gTV8uFcUse3RF5__8JbMmkpb4bGujN_mvlNAZhZyabRqhsg0Xa-ibfbdyR",
--  "tokenType": "Bearer",
--  "username": "admin"
--}


--   "username": "recepcion",
--   "password": "RecepcionDemo2026!"
-- {
--   "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyZWNlcGNpb24iLCJpYXQiOjE3OTU1Mzk3ODksImV4cCI6MTc5NTYyNjE4OX0.ZAc2wtXfwGLrE2aBGx4kmeJjAL29VnYI5cSAvEqAAs9BcQb5KntV63nG7Jd2xbz7",
--   "tokenType": "Bearer",
--   "username": "recepcion"
-- }
-- {
--   "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsYXVyYSIsImlhdCI6MTc5NTY1ODY3MywiZXhwIjoxNzk1NzQ1MDczfQ.m4Np5hcLn6d_SaoECv2OZmv83YP0Qdo3cD7LmWyCpK25wjmBfkcnGSmCVP6Jlhao",
--   "tokenType": "Bearer",
--   "username": "laura"
-- }






-- http://localhost:8080/doc/swagger-ui.html