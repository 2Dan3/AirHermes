DROP SCHEMA IF EXISTS airhermes;
CREATE SCHEMA airhermes DEFAULT CHARACTER SET utf8;
USE airhermes;

CREATE TABLE locations (
    id BIGINT,
    city VARCHAR(40),
    state VARCHAR(40),
    continent VARCHAR(40),
    image_path VARCHAR(200),
    PRIMARY KEY(id)
);

CREATE TABLE airplanes (
	id BIGINT,
    name VARCHAR(40) NOT NULL,
    seat_rows INT NOT NULL,
    seat_columns INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE airports (
    id BIGINT AUTO_INCREMENT,
    airport_code_name VARCHAR(3),
    location_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(location_id) REFERENCES locations(id)
);

CREATE TABLE discounts_standard (
    id BIGINT,
--    check line
    discount_coefficient DECIMAL(10, 2) DEFAULT 0,
    valid_until_date DATETIME NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE flights (
    id BIGINT AUTO_INCREMENT,
    airport_departure_id BIGINT,
    airport_destination_id BIGINT,
    airplane_id BIGINT,
    departure_timestamp DATETIME,
    flight_duration_minutes INT,
    flight_ticket_price INT,
    discount_standard_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(airport_departure_id) REFERENCES airports(id),
    FOREIGN KEY(airport_destination_id) REFERENCES airports(id),
    FOREIGN KEY(airplane_id) REFERENCES airplanes(id),
    FOREIGN KEY(discount_standard_id) REFERENCES discounts_standard(id)
);

CREATE TABLE flight_cancellations (
    id BIGINT,
    flight_cancelled_id BIGINT,
    reason_of_cancellation VARCHAR(80),
    PRIMARY KEY(id),
    FOREIGN KEY(flight_cancelled_id) REFERENCES flights(id)
);

CREATE TABLE flight_reservations (
    id BIGINT,
    reservation_creation_timestamp DATETIME,
    sum_price_of_flight_tickets INT,
    PRIMARY KEY(id)
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
    id BIGINT,
    flight_id BIGINT,
    seat_number INT,
    flight_ticket_price INT,
    passenger_name_surname VARCHAR(70),
--    check paranthesis
    passport_number INT(9),
    PRIMARY KEY(id),
    FOREIGN KEY(flight_id) REFERENCES flights(id)
);

CREATE TABLE users (
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    date_of_birth DATETIME,
    registration_timestamp DATETIME,
    admin BOOL DEFAULT false,
    blocked BOOL DEFAULT false,
    PRIMARY KEY(username)
);

CREATE TABLE loyalty_cards (
    id BIGINT,
    granted_discount_coefficient DECIMAL(10, 2),
    points_collected INT,
    user_username VARCHAR(30),
    PRIMARY KEY(id),
    FOREIGN KEY(user_username) REFERENCES users(username)
);

CREATE TABLE loyalty_card_creation_requests (
    user_username VARCHAR(30),
    status VARCHAR(8),
    PRIMARY KEY(user_username),
    FOREIGN KEY(user_username) REFERENCES users(username)
);

CREATE TABLE shopping_carts (
    user_username VARCHAR(30),
    flight_ticket_id BIGINT,
    PRIMARY KEY(user_username, flight_ticket_id),
    FOREIGN KEY(user_username) REFERENCES users(username)
        ON DELETE CASCADE,
    FOREIGN KEY(flight_ticket_id) REFERENCES flight_tickets(id)
        ON DELETE CASCADE
);

CREATE TABLE wish_list_of_flights (
    user_username VARCHAR(30),
    flight_id BIGINT,
    PRIMARY KEY(user_username, flight_id),
    FOREIGN KEY(user_username) REFERENCES users(username)
        ON DELETE CASCADE,
    FOREIGN KEY(flight_id) REFERENCES flights(id)
        ON DELETE CASCADE
);


INSERT INTO
users (username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked)
VALUES ('dan23', 'D', 'U', '123', 'dan23.ftn@gmail.com', '2001-12-23 00:00', '2024-12-23 13:22', 1, 0);

INSERT INTO
users (username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked)
VALUES ('mar23', 'Marko', 'Markovic', '123', 'mar23.ftn@gmail.com', '2008-12-23 00:00', '2024-12-23 21:48', 0, 1);

INSERT INTO
users (username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked)
VALUES ('mil23', 'Milica', 'B', '123', 'mil23.ftn@gmail.com', '1998-12-23 00:00', '2024-01-23 18:11', 0, 0);

INSERT INTO
users (username, name, surname, password, email, date_of_birth, registration_timestamp, admin, blocked)
VALUES ('iva23', 'Ivana', 'P', '123', 'iva23.ftn@gmail.com', '2001-03-23 00:00', '2025-09-23 09:58', 0, 0);

INSERT INTO
airplanes (name, seat_rows, seat_columns)
VALUES ('');