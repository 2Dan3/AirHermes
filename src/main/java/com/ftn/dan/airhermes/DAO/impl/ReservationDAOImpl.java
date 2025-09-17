package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.ReservationDAO;
import com.ftn.dan.airhermes.model.entity.FlightReservation;
import com.ftn.dan.airhermes.model.entity.FlightTicket;
import com.ftn.dan.airhermes.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int updateTotalPrice(FlightReservation reservation, double price) {
        int success = 0;

        String sql = "UPDATE flight_reservations SET sum_price_of_flight_tickets = ? WHERE id = ?";
        success = jdbcTemplate.update(sql, price, reservation.getId());

        return success;
    }

    @Transactional
    @Override
    public Long save(FlightReservation reservation, Long[] flightIDs) {
//        1) save to "flight_reservations"

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO flight_reservations (reservation_creation_timestamp, sum_price_of_flight_tickets, user_id) VALUES (?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;

                preparedStatement.setTimestamp(index++, reservation.getReservationCreationTimestamp());
                preparedStatement.setInt(index++, reservation.getSumPriceOfFlightTickets());

                if (reservation.getUser() != null)
                    preparedStatement.setLong(index++, reservation.getUser().getId());
                else
                    preparedStatement.setNull(index++, Types.BIGINT);

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;

//        2) save to "flight_flight_reservation"

        if (success) {

            String sql = "INSERT INTO flight_flight_reservation (flight_id, flight_reservation_id) VALUES (?, ?)";
            for (Long flightID : flightIDs) {
                success = success && jdbcTemplate.update(sql, flightID, keyHolder.getKey()) == 1;
            }
        }

        return success ? keyHolder.getKey().longValue() : null;
    }


}
