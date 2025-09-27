package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.FlightDAO;
import com.ftn.dan.airhermes.model.dto.FlightDTO;
import com.ftn.dan.airhermes.model.dto.ReportDTO;
import com.ftn.dan.airhermes.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FlightDAOImpl implements FlightDAO {

    private final String SQL_GET_ALL_FLIGHTS_AND_REFERENCES =
            "SELECT f.id, f.departure_timestamp, f.flight_duration_minutes, f.flight_ticket_price, " +
            "adep.airport_code_name, ades.airport_code_name, " +
            "ldep.id, ldep.city, ldep.state, ldep.continent, ldep.image_path, " +
            "ldes.id, ldes.city, ldes.state, ldes.continent, ldes.image_path, " +
            "av.id, av.name, av.seat_rows, av.seat_columns, " +
            "d.id, d.discount_coefficient, d.valid_until_date, " +
            "c.flight_cancelled_id " +
            "FROM flights f " +
            "LEFT JOIN airports adep ON adep.airport_code_name = f.airport_departure_code_name " +
            "LEFT JOIN airports ades ON ades.airport_code_name = f.airport_destination_code_name " +
            "LEFT JOIN airplanes av ON av.id = f.airplane_id " +
            "LEFT JOIN locations ldep ON ldep.id = adep.location_id " +
            "LEFT JOIN locations ldes ON ldes.id = ades.location_id " +
            "LEFT JOIN discounts_standard d ON d.id = f.discount_standard_id " +
            "LEFT JOIN flight_cancellations c ON c.flight_cancelled_id = f.id ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class FlightRowMapper implements RowMapper<Flight> {

        @Override
        public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long flight_uid = rs.getLong(index++);
            Timestamp departure_timestamp = rs.getTimestamp(index++);
            Integer flight_duration_minutes = rs.getInt(index++);
            Integer flight_ticket_price = rs.getInt(index++);

            String airport_code_name_departure = rs.getString(index++);
            String airport_code_name_destination = rs.getString(index++);

            Long location_id_departure = rs.getLong(index++);
            String location_city_departure = rs.getString(index++);
            String location_state_departure = rs.getString(index++);
            String location_continent_departure = rs.getString(index++);
            String imagePathDeparture = rs.getString(index++);

            Long location_id_destination = rs.getLong(index++);
            String location_city_destination = rs.getString(index++);
            String location_state_destination = rs.getString(index++);
            String location_continent_destination = rs.getString(index++);
            String imagePathDestination = rs.getString(index++);

            Long airplane_id = rs.getLong(index++);
            String airplane_name = rs.getString(index++);
            Integer seat_rows = rs.getInt(index++);
            Integer seat_columns = rs.getInt(index++);

            Long discount_id = rs.getLong(index++);
            Double discount_coefficient = rs.getDouble(index++);
            Timestamp valid_until_date = rs.getTimestamp(index++);

            DiscountStandard discount = new DiscountStandard(discount_id, discount_coefficient, valid_until_date);
            Airplane airplane = new Airplane(airplane_id, airplane_name, seat_rows, seat_columns);
            Location locationDeparture = new Location(location_id_departure, location_city_departure, location_state_departure, location_continent_departure, imagePathDeparture);
            Location locationDestination = new Location(location_id_destination, location_city_destination, location_state_destination, location_continent_destination, imagePathDestination);
            Airport airportDeparture = new Airport(airport_code_name_departure, locationDeparture);
            Airport airportDestination = new Airport(airport_code_name_destination, locationDestination);

            Flight flight = new Flight(flight_uid, departure_timestamp, flight_duration_minutes, flight_ticket_price, airplane, airportDeparture, airportDestination, discount);
            return flight;
        }

    }

    @Override
    public List<Flight> find(Long flight_id, Timestamp departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights) {

        final String all_discounted_flights = " WHERE f.discount_standard_id IS NOT NULL AND c.flight_cancelled_id IS NULL";

        String sql = SQL_GET_ALL_FLIGHTS_AND_REFERENCES;

        ArrayList<Object> listaArgumenata = new ArrayList<Object>();

        StringBuffer whereSql = new StringBuffer(" WHERE c.flight_cancelled_id IS NULL AND ");
        boolean imaArgumenata = false;

        if(departureTimestamp != null) {
            Timestamp timestamp_MIN;
            Timestamp timestamp_MAX;

            if(lookForSimilarTimingFlights) {
              timestamp_MIN = Timestamp.valueOf(departureTimestamp.toLocalDateTime().minusDays(2));
              timestamp_MAX = Timestamp.valueOf(departureTimestamp.toLocalDateTime().plusDays(2));
             }
            else {
              timestamp_MIN = departureTimestamp;
              timestamp_MAX = timestamp_MIN;
             }

            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("f.departure_timestamp >= ? AND f.departure_timestamp <= ?");
            imaArgumenata = true;
            listaArgumenata.add(timestamp_MIN);
            listaArgumenata.add(timestamp_MAX);
        }

        if(departureAirportOrCityOrStateSearchTerm != null) {
            departureAirportOrCityOrStateSearchTerm = "%" + departureAirportOrCityOrStateSearchTerm + "%";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("(adep.airport_code_name LIKE ? OR ldep.city LIKE ? OR ldep.state LIKE ?)");
            imaArgumenata = true;
            listaArgumenata.add(departureAirportOrCityOrStateSearchTerm);
            listaArgumenata.add(departureAirportOrCityOrStateSearchTerm);
            listaArgumenata.add(departureAirportOrCityOrStateSearchTerm);
        }

        if(destinationAirportOrCityOrStateSearchTerm!=null) {
            destinationAirportOrCityOrStateSearchTerm = "%" + destinationAirportOrCityOrStateSearchTerm + "%";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("(ades.airport_code_name LIKE ? OR ldes.city LIKE ? OR ldes.state LIKE ?)");
            imaArgumenata = true;
            listaArgumenata.add(destinationAirportOrCityOrStateSearchTerm);
            listaArgumenata.add(destinationAirportOrCityOrStateSearchTerm);
            listaArgumenata.add(destinationAirportOrCityOrStateSearchTerm);
        }

        if(passengers != null) {
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("? <= ( (av.seat_rows * av.seat_columns) - (SELECT COUNT(ft.id) FROM flight_tickets ft WHERE ft.flight_id = f.id))");
            imaArgumenata = true;
            listaArgumenata.add(passengers);
        }

        if(imaArgumenata)
            sql = sql + whereSql.toString();
        else
            sql = sql + all_discounted_flights;

        sql = sql + " ORDER BY f.departure_timestamp";
        System.out.println("DAO find: " + sql);

        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new FlightDAOImpl.FlightRowMapper());
    }

    @Override
    public List<Flight> findAllBy(Long[] flightIds) {
        System.out.println(Arrays.toString(flightIds));
        String sql = SQL_GET_ALL_FLIGHTS_AND_REFERENCES;

        ArrayList<Object> listaArgumenata = new ArrayList<Object>();
        boolean imaArgumenata = false;

        StringBuffer whereSql = new StringBuffer(" WHERE c.flight_cancelled_id IS NULL ");

        for (Long flightId : flightIds) {
            if (imaArgumenata)
                whereSql.append(" OR ");
            else
                whereSql.append(" AND ( ");
            whereSql.append("f.id = ?");
            listaArgumenata.add(flightId);
            imaArgumenata = true;
        }

        if (flightIds.length != 0)
            whereSql.append(" ) ");

        if(imaArgumenata)
            sql = sql + whereSql.toString();

        sql = sql + " ORDER BY f.departure_timestamp";
        System.out.println("DAO findAllBy: " + sql);

        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new FlightRowMapper());
    }

    @Override
    public List<FlightDTO> findFlightsFromWishlist(User user) {
        final String sql = "SELECT f.id, f.departure_timestamp, f.flight_duration_minutes, f.flight_ticket_price, " +
                "adep.airport_code_name, ades.airport_code_name, " +
                "ldep.id, ldep.city, ldep.state, ldep.continent, ldes.id, ldes.city, ldes.state, ldes.continent, " +
                "av.id, av.name, av.seat_rows, av.seat_columns, " +
                "d.id, d.discount_coefficient, d.valid_until_date, " +
                "(SELECT ( (av.seat_rows * av.seat_columns) <= (SELECT COUNT(ft.id) FROM flight_tickets ft WHERE ft.flight_id = f.id)) ) AS flight_sold_out " +
                "FROM flights f " +
                "LEFT JOIN airports adep ON adep.airport_code_name = f.airport_departure_code_name " +
                "LEFT JOIN airports ades ON ades.airport_code_name = f.airport_destination_code_name " +
                "LEFT JOIN airplanes av ON av.id = f.airplane_id " +
                "LEFT JOIN locations ldep ON ldep.id = adep.location_id " +
                "LEFT JOIN locations ldes ON ldes.id = ades.location_id " +
                "LEFT JOIN discounts_standard d ON d.id = f.discount_standard_id " +
                "LEFT JOIN wish_list_of_flights w ON w.flight_id = f.id " +
                "WHERE w.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{user.getId()}, new FlightDTORowMapper());
    }

    @Override
    public List<ReportDTO> findFlightsAndRevenueForInterval(Timestamp timestampMin, Timestamp timestampMax) {
        ArrayList<Object> listaArgumenata = new ArrayList<Object>();

        StringBuffer whereSql = new StringBuffer(" WHERE c.flight_cancelled_id IS NULL AND ");
        boolean imaArgumenata = false;

        String sql = "SELECT " +
                "f.id AS flight_id, " +
                "    f.departure_timestamp AS departure_time, " +
                "    (av.seat_rows*av.seat_columns) AS seats_total, " +
                "    tickets.ticket_count AS seats_sold, " +
                "    tickets.ticket_prices_total AS total_flight_revenue, " +
                "    c.flight_cancelled_id " +
                "FROM flights f " +
                "LEFT JOIN " +
                "airplanes av " +
                "ON av.id = f.airplane_id " +
                "LEFT JOIN " +
                "(SELECT " +
                "COUNT(ft.id) AS ticket_count, " +
                "        SUM(ft.flight_ticket_price) AS ticket_prices_total, " +
                "        ft.flight_id AS fl_id " +
                "FROM flight_tickets ft " +
                "    GROUP BY ft.flight_id) " +
                "    AS tickets " +
                "ON tickets.fl_id = f.id " +
                "LEFT JOIN flight_cancellations c " +
                "ON c.flight_cancelled_id = f.id";

        if(timestampMin != null) {
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("f.departure_timestamp >= ?");
            imaArgumenata = true;
            listaArgumenata.add(timestampMin);
        }
        if(timestampMax != null) {
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("f.departure_timestamp <= ?");
            imaArgumenata = true;
            listaArgumenata.add(timestampMax);
        }

        if(imaArgumenata)
            sql = sql + whereSql.toString();

        System.out.println("DAO reports: " + sql);
        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new ReportDTORowMapper());
    }

    @Override
    public void cancelFlight(Flight flight, String reasonOfCancellation) {
        String sql = "INSERT INTO flight_cancellations (flight_cancelled_id, reason_of_cancellation) VALUES (?, ?)";
        jdbcTemplate.update(sql, flight.getId(), reasonOfCancellation);
    }

    @Override
    public void save(Long flightID, String airportDepartureCodeName, String airportDestinationCodeName, Long airplaneID, LocalDateTime departureLocalDateTime, Integer flightDurationMinutes, Integer flightTicketPrice, Long discountStandardID) {
        String sql = "INSERT INTO flights (id, airport_departure_code_name, airport_destination_code_name, airplane_id, departure_timestamp, flight_duration_minutes, flight_ticket_price, discount_standard_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, flightID, airportDepartureCodeName, airportDestinationCodeName, airplaneID, Timestamp.valueOf(departureLocalDateTime), flightDurationMinutes, flightTicketPrice, discountStandardID);
    }

    @Override
    public void update(Long flightID, String airportDepartureCodeName, String airportDestinationCodeName, Long airplaneID, LocalDateTime departureLocalDateTime, Integer flightDurationMinutes, Integer flightTicketPrice, Long discountStandardID) {
        String sql = "UPDATE flights SET airport_departure_code_name = ?, airport_destination_code_name = ?, airplane_id = ?, departure_timestamp = ?, flight_duration_minutes = ?, flight_ticket_price = ?, discount_standard_id = ? WHERE id = ?";
        int success = jdbcTemplate.update(sql, airportDepartureCodeName, airportDestinationCodeName, airplaneID, Timestamp.valueOf(departureLocalDateTime), flightDurationMinutes, flightTicketPrice, discountStandardID, flightID);
//        return success == 0?false:true;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM flights WHERE id = ?";
        int success = jdbcTemplate.update(sql, id);
//        return success == 0?false:true;
    }

    private class ReportDTORowMapper implements RowMapper<ReportDTO> {

        @Override
        public ReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long flight_uid = rs.getLong(index++);
            Timestamp departure_timestamp = rs.getTimestamp(index++);
            Long flight_seats_total = rs.getLong(index++);
            Long flight_tickets_sold = rs.getLong(index++);
            Double total_flight_revenue = rs.getDouble(index++);

            ReportDTO reportDTO = new ReportDTO(flight_uid, departure_timestamp, flight_seats_total, flight_tickets_sold, total_flight_revenue);
            return reportDTO;
        }
    }

    private class FlightDTORowMapper implements RowMapper<FlightDTO> {

        @Override
        public FlightDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long flight_uid = rs.getLong(index++);
            Timestamp departure_timestamp = rs.getTimestamp(index++);
            Integer flight_duration_minutes = rs.getInt(index++);
            Integer flight_ticket_price = rs.getInt(index++);

            String airport_code_name_departure = rs.getString(index++);
            String airport_code_name_destination = rs.getString(index++);

            Long location_id_departure = rs.getLong(index++);
            String location_city_departure = rs.getString(index++);
            String location_state_departure = rs.getString(index++);
            String location_continent_departure = rs.getString(index++);

            Long location_id_destination = rs.getLong(index++);
            String location_city_destination = rs.getString(index++);
            String location_state_destination = rs.getString(index++);
            String location_continent_destination = rs.getString(index++);

            Long airplane_id = rs.getLong(index++);
            String airplane_name = rs.getString(index++);
            Integer seat_rows = rs.getInt(index++);
            Integer seat_columns = rs.getInt(index++);

            Long discount_id = rs.getLong(index++);
            Double discount_coefficient = rs.getDouble(index++);
            Timestamp valid_until_date = rs.getTimestamp(index++);

            Integer sold_out = rs.getInt(index++);
            boolean soldOut = sold_out == 1;

            DiscountStandard discount = new DiscountStandard(discount_id, discount_coefficient, valid_until_date);
            Airplane airplane = new Airplane(airplane_id, airplane_name, seat_rows, seat_columns);
            Location locationDeparture = new Location(location_id_departure, location_city_departure, location_state_departure, location_continent_departure);
            Location locationDestination = new Location(location_id_destination, location_city_destination, location_state_destination, location_continent_destination);
            Airport airportDeparture = new Airport(airport_code_name_departure, locationDeparture);
            Airport airportDestination = new Airport(airport_code_name_destination, locationDestination);

            FlightDTO flightDTO = new FlightDTO(flight_uid, departure_timestamp, flight_duration_minutes, flight_ticket_price, airplane, airportDeparture, airportDestination, discount, soldOut);
            return flightDTO;
        }

    }
}
