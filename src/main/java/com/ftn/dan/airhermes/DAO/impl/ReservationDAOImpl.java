package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.ReservationDAO;
import com.ftn.dan.airhermes.model.dto.ReservationDTO;
import com.ftn.dan.airhermes.model.entity.FlightReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ReservationRowMapper implements RowMapper<ReservationDTO> {

        @Override
        public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long flightId = rs.getLong(index++);
            String airportDepartureCodeName = rs.getString(index++);
            String airportDestinationCodeName = rs.getString(index++);
            Timestamp flightDepartureTimestamp = rs.getTimestamp(index++);
            Long reservationId = rs.getLong(index++);
            Timestamp reservationCreationTimestamp = rs.getTimestamp(index++);
            Double reservationSumPriceOfAllTickets = rs.getDouble(index++);
            Long flightCancellationId = rs.getLong(index++);

            Boolean cancelled = flightCancellationId != 0;
            ReservationDTO reservationDTO = new ReservationDTO(flightId, airportDepartureCodeName, airportDestinationCodeName, flightDepartureTimestamp, reservationId, reservationCreationTimestamp, reservationSumPriceOfAllTickets, cancelled);
            return reservationDTO;
        }

    }

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

    @Override
    public List<ReservationDTO> findAllWithFlightsByUserId(Long userId) {
        String sql = "SELECT f.id, f.airport_departure_code_name, f.airport_destination_code_name, f.departure_timestamp, " +
                "r.id, r.reservation_creation_timestamp, r.sum_price_of_flight_tickets, " +
                "c.flight_cancelled_id " +
                "FROM flights f " +
                "LEFT JOIN flight_flight_reservation fr " +
                "ON f.id = fr.flight_id " +
                "LEFT JOIN flight_reservations r " +
                "ON r.id = fr.flight_reservation_id " +
                "LEFT JOIN flight_cancellations c " +
                "ON c.flight_cancelled_id = f.id " +
                "WHERE r.user_id = ? " +
                "ORDER BY r.reservation_creation_timestamp DESC";
            return jdbcTemplate.query(sql, new Object[]{userId}, new ReservationRowMapper());
    }

    @Override
    public boolean existsReservationForFlight(Long flightID) {
        final String sql = "SELECT EXISTS (SELECT fr.flight_reservation_id FROM flight_flight_reservation fr WHERE fr.flight_id = ?) AS 'exists';";
        return jdbcTemplate.queryForObject(sql, Integer.class, flightID) == 0 ? false : true;
    }

    @Override
    public boolean notEnoughSeats(int seats, Long flight1Id) {
        String sql =
                "SELECT ? > ( (av.seat_rows * av.seat_columns) - (SELECT COUNT(ft.id) FROM flight_tickets ft WHERE ft.flight_id = f.id AND f.id = ?)) " +
                " FROM flights f " +
                "LEFT JOIN airplanes av ON av.id = f.airplane_id " +
                "WHERE f.id = ?";
        return jdbcTemplate.queryForObject(sql, Boolean.class, seats, flight1Id, flight1Id);
    }
}
