-- Test version for development - Projekat AIRPORT:

DROP SCHEMA IF EXISTS airhermes;
CREATE SCHEMA airhermes DEFAULT CHARACTER SET utf8;
USE airhermes;

CREATE TABLE users (
	id BIGINT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    date_of_birth DATETIME,
    registration_timestamp DATETIME,
    admin BOOL DEFAULT false,
    blocked BOOL DEFAULT false,
    PRIMARY KEY(id)
);

CREATE TABLE locations (
    id BIGINT AUTO_INCREMENT,
    city VARCHAR(40),
    state VARCHAR(40),
    continent VARCHAR(40),
    image_path VARCHAR(200),
    PRIMARY KEY(id)
);

CREATE TABLE airplanes (
	id BIGINT AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    seat_rows INT NOT NULL,
    seat_columns INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE airports (
    airport_code_name VARCHAR(3),
    location_id BIGINT,
    PRIMARY KEY(airport_code_name),
    FOREIGN KEY(location_id) REFERENCES locations(id)
);

CREATE TABLE discounts_standard (
    id BIGINT AUTO_INCREMENT,
--    check line
    discount_coefficient DECIMAL(10, 2) DEFAULT 0,
    valid_until_date DATETIME NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE flights (
    id BIGINT AUTO_INCREMENT,
    airport_departure_code_name VARCHAR(3),
    airport_destination_code_name VARCHAR(3),
    airplane_id BIGINT,
    departure_timestamp DATETIME,
    flight_duration_minutes INT,
    flight_ticket_price INT,
    discount_standard_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(airport_departure_code_name) REFERENCES airports(airport_code_name),
    FOREIGN KEY(airport_destination_code_name) REFERENCES airports(airport_code_name),
    FOREIGN KEY(airplane_id) REFERENCES airplanes(id),
    FOREIGN KEY(discount_standard_id) REFERENCES discounts_standard(id)
);

CREATE TABLE flight_cancellations (
    flight_cancelled_id BIGINT AUTO_INCREMENT,
    reason_of_cancellation VARCHAR(80),
    PRIMARY KEY(flight_cancelled_id),
    FOREIGN KEY(flight_cancelled_id) REFERENCES flights(id) 
		 ON DELETE CASCADE
);

CREATE TABLE flight_reservations (
    id BIGINT AUTO_INCREMENT,
    reservation_creation_timestamp DATETIME,
    sum_price_of_flight_tickets INT,
    user_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES users(id) 
		ON DELETE CASCADE
);

CREATE TABLE flight_flight_reservation (
    flight_id BIGINT,
    flight_reservation_id BIGINT,
    PRIMARY KEY(flight_id, flight_reservation_id),
    FOREIGN KEY(flight_id) REFERENCES flights(id)
        ON DELETE CASCADE,
    FOREIGN KEY(flight_reservation_id) REFERENCES flight_reservations(id)
        ON DELETE CASCADE
);

CREATE TABLE flight_tickets (
    id BIGINT AUTO_INCREMENT,
    flight_id BIGINT,
    seat_number INT,
    flight_ticket_price INT,
    passenger_name VARCHAR(30),
    passenger_surname VARCHAR(40),
--    check or omit (9) in later versions of MySQL
    passport_number INT(9),
    flight_reservation_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(flight_id) REFERENCES flights(id),
    FOREIGN KEY(flight_reservation_id) REFERENCES flight_reservations(id) 
		 ON DELETE CASCADE
);

CREATE TABLE loyalty_cards (
    id BIGINT AUTO_INCREMENT,
    spent_money_unconverted_to_points DECIMAL(12, 2),
    points_collected INT,
    user_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE loyalty_card_creation_requests (
    user_id BIGINT,
    status VARCHAR(8),
    PRIMARY KEY(user_id),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE shopping_carts (
    user_id BIGINT,
    flight_ticket_id BIGINT,
    PRIMARY KEY(user_id, flight_ticket_id),
    FOREIGN KEY(user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY(flight_ticket_id) REFERENCES flight_tickets(id)
        ON DELETE CASCADE
);

CREATE TABLE wish_list_of_flights (
    user_id BIGINT,
    flight_id BIGINT,
    PRIMARY KEY(user_id, flight_id),
    FOREIGN KEY(user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY(flight_id) REFERENCES flights(id)
        ON DELETE CASCADE
);


-- INDEXES:

-- Helps with A.origin filter and A.departure_time sort/filter
-- CREATE INDEX idx_origin_departure ON flights (origin, departure_time);

-- Helps with join from A.destination to B.origin and B.departure_time filtering
-- CREATE INDEX idx_origin_departure_duration ON flights (origin, departure_time, flight_duration_in_minutes);

-- Helps with B.destination to C.origin and C.departure_time filtering
-- (same structure as above, but ensures index is useful for both join and time filter)
-- CREATE INDEX idx_destination_departure_duration ON flights (destination, departure_time, flight_duration_in_minutes);

-- 


INSERT INTO 
users (id, username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked) 
VALUES (1, 'dan23', 'D', 'U', '123', 'dan23.ftn@gmail.com', '2001-12-23 00:00', '2024-12-23 13:22', 1, 0);
INSERT INTO 
users (id, username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked) 
VALUES (2, 'mar23', 'Marko', 'Markovic', '123', 'mar23.ftn@gmail.com', '2008-12-23 00:00', '2024-12-23 21:48', 0, 0);
INSERT INTO 
users (id, username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked) 
VALUES (3, 'mil23', 'Milica', 'B', '123', 'mil23.ftn@gmail.com', '1998-12-23 00:00', '2024-01-23 18:11', 0, 0);
INSERT INTO 
users (id, username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked) 
VALUES (4, 'iva23', 'Ivana', 'P', '123', 'iva23.ftn@gmail.com', '2001-03-23 00:00', '2025-09-23 09:58', 0, 1);


INSERT INTO 
airplanes (id, name, seat_rows, seat_columns) 
VALUES (1, 'Airbus A320', 30, 6);
INSERT INTO 
airplanes (id, name, seat_rows, seat_columns) 
VALUES (2, 'Boeing 737', 31, 6);
INSERT INTO 
airplanes (id, name, seat_rows, seat_columns) 
VALUES (3, 'Airbus A350', 40, 9);
INSERT INTO 
airplanes (id, name, seat_rows, seat_columns) 
VALUES (4, 'Airbus A330', 32, 8);
INSERT INTO 
airplanes (id, name, seat_rows, seat_columns) 
VALUES (5, 'ATR 72', 18, 4);


INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (1, 'Belgrade', 'Serbia', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (2, 'Zagreb', 'Croatia', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (3, 'Toronto', 'Ontario, Canada', 'North America', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (4, 'New York City', 'New York, United States', 'North America', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (5, 'London', 'England, United Kingdom', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (6, 'Moscow', 'Russia', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (7, 'Istanbul', 'Turkey', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (8, 'Warsaw', 'Poland', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (9, 'Rome', 'Lazio, Italy', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (10, 'Milan', 'Lombardy, Italy', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (11, 'Madrid', 'Spain', 'Europe', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (12, 'Tokyo', 'Honshu, Japan', 'Asia', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (13, 'Antanànarìvo', 'Madagascar', 'Africa', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (14, 'Sydney', 'Australia', 'Australia', 'iwdaojd');
INSERT INTO 
locations (id, city, state, continent, image_path) 
VALUES (15, 'Rio de Janeiro', 'Brazil', 'South America', 'iwdaojd');


INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('BEG', 1);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('ZAG', 2);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('YYZ', 3);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('JFK', 4);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('LHR', 5);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('SVO', 6);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('IST', 7);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('WAW', 8);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('FCO', 9);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('MXP', 10);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('MAD', 11);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('HND', 12);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('TNR', 13);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('SYD', 14);
INSERT INTO 
airports (airport_code_name, location_id) 
VALUES ('RRJ', 15);



-- Example of flight reservation with a general-discount (admin-defined discount for everyone) claimed.

INSERT INTO 
discounts_standard (id, discount_coefficient, valid_until_date) 
VALUES (1, 0.5, '2026-09-09 00:00');
INSERT INTO 
discounts_standard (id, discount_coefficient, valid_until_date) 
VALUES (2, 0.2, '2026-09-09 00:00');
INSERT INTO 
discounts_standard (id, discount_coefficient, valid_until_date) 
VALUES (3, 0.3, '2024-09-09 00:00');


INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (1, 'JFK', 'BEG', 1, '2026-09-11 10:00', 360, 70000, 1);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (2, 'BEG', 'SVO', 2, '2026-09-11 17:00', 240, 40000, 1);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (3, 'SVO', 'SYD', 3, '2026-09-11 22:00', 240, 50000, 3);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (4, 'BEG', 'IST', 4, '2026-09-11 17:00', 120, 30000, 2);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (5, 'IST', 'SYD', 5, '2026-09-11 19:00', 180, 40000, null);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (6, 'BEG', 'SVO', 1, '2026-09-11 21:00', 341, 70000, 2);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (7, 'BEG', 'SVO', 1, '2026-09-11 20:00', 325, 70000, 2);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (8, 'RRJ', 'MAD', 3, '2026-09-11 10:00', 200, 30000, 1);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (9, 'MAD', 'HND', 4, '2026-09-11 14:30', 310, 50000, 2);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (10, 'RRJ', 'IST', 1, '2026-09-11 15:00', 240, 40000, 3);
INSERT INTO 
flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) 
VALUES (11, 'IST', 'HND', 5, '2026-09-11 16:00', 312, 40000, null);



INSERT INTO 
flight_reservations (id, reservation_creation_timestamp, sum_price_of_flight_tickets, user_id) 
VALUES (1, '2025-09-05 00:00', 40000, 1);

INSERT INTO 
flight_flight_reservation (flight_id, flight_reservation_id) 
VALUES (2, 1);

INSERT INTO 
flight_tickets (id, flight_id, seat_number, flight_ticket_price, passenger_name, passenger_surname, passport_number, flight_reservation_id) 
VALUES (1, 2, 10, 20000, 'Marko', 'Markovic', 123456789, 1);
INSERT INTO 
flight_tickets (id, flight_id, seat_number, flight_ticket_price, passenger_name, passenger_surname, passport_number, flight_reservation_id) 
VALUES (2, 2, 11, 20000, 'Milica', 'B', 987654321, 1);


INSERT INTO 
loyalty_card_creation_requests (user_id, status) 
VALUES (2, 'SENT');

-- additional test data: 1 flight_cancellation & 3 wish_list_of_flights (may remove if we are adding values programatically anyways upon project app demonstration)
insert into flight_cancellations(flight_cancelled_id, reason_of_cancellation) values(2, 'Airplane issues');

insert into wish_list_of_flights(flight_id, user_id) values (2, 2);
insert into wish_list_of_flights(flight_id, user_id) values (1, 2);
insert into wish_list_of_flights(flight_id, user_id) values (3, 2);