SHOW CREATE TABLE locations;
SHOW CREATE TABLE rooms;
SHOW CREATE TABLE equipment;
SHOW CREATE TABLE room_equipment;

SHOW CREATE TABLE membership_plans;
SHOW CREATE TABLE members;
SHOW CREATE TABLE user_accounts;

SHOW CREATE TABLE bookings;
SHOW CREATE TABLE booking_items;
SHOW CREATE TABLE additional_services;




SELECT COUNT(*) AS total_ubicaciones FROM locations;
SELECT COUNT(*) AS total_habitaciones FROM rooms;
SELECT COUNT(*) AS total_equipos FROM equipment;
SELECT COUNT(*) AS total_planes FROM membership_plans;
SELECT COUNT(*) AS total_miembros FROM members;
SELECT COUNT(*) AS total_cuentas FROM user_accounts;
SELECT COUNT(*) AS total_reservas FROM bookings;
SELECT COUNT(*) AS total_items FROM booking_items;

SELECT status, COUNT(*) AS cantidad
FROM bookings
GROUP BY status;

SELECT * FROM locations;
SELECT * FROM rooms;


SELECT COUNT(*) AS total_ubicaciones FROM locations;
SELECT COUNT(*) AS total_habitaciones FROM rooms;
SELECT COUNT(*) AS total_reservas FROM bookings;
SELECT COUNT(*) AS total_servicios_reserva FROM booking_items;

