package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.TicketDAO;
import com.ftn.dan.airhermes.model.entity.FlightTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class TicketDAOImpl implements TicketDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int saveAll(Long reservationID, List<FlightTicket> tickets) {
//        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
//
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                String sql = "INSERT INTO flight_reservations (reservation_creation_timestamp, trajanje) VALUES (?, ?)";
//
//                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                int index = 1;
//                preparedStatement.setString(index++, film.getNaziv());
//                preparedStatement.setInt(index++, film.getTrajanje());
//
//                return preparedStatement;
//            }
//
//        };
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
//        if (success) {

            boolean success = true;
            String sql = "INSERT INTO flight_tickets (flight_id, seat_number, flight_ticket_price, passenger_name, passenger_surname, passport_number, flight_reservation_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            for (FlightTicket iterTicket : tickets) {
                success = success && jdbcTemplate.update(sql, iterTicket.getFlight().getId(), iterTicket.getSeatNumber(), iterTicket.getFlightTicketPrice(), iterTicket.getPassengerName(), iterTicket.getPassengerSurname(), iterTicket.getPassportNumber(), reservationID) == 1;
            }
//        }
//        return success;
        return success?1:0;
    }
}
