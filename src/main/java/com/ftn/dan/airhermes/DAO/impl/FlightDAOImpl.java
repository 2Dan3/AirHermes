package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.FlightDAO;
import com.ftn.dan.airhermes.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class FlightDAOImpl implements FlightDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class FlightRowMapper implements RowMapper<Flight> {

        @Override
        public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long flight_uid = rs.getLong(index++);
            Date departure_timestamp = rs.getDate(index++);
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
            Date valid_until_date = rs.getDate(index++);

            DiscountStandard discount = new DiscountStandard(discount_id, discount_coefficient, valid_until_date);
            Airplane airplane = new Airplane(airplane_id, airplane_name, seat_rows, seat_columns);
            Location locationDeparture = new Location(location_id_departure, location_city_departure, location_state_departure, location_continent_departure);
            Location locationDestination = new Location(location_id_destination, location_city_destination, location_state_destination, location_continent_destination);
            Airport airportDeparture = new Airport(airport_code_name_departure, locationDeparture);
            Airport airportDestination = new Airport(airport_code_name_destination, locationDestination);

            Flight flight = new Flight(flight_uid, departure_timestamp, flight_duration_minutes, flight_ticket_price, airplane, airportDeparture, airportDestination, discount);
            return flight;
        }

    }

    @Override
    public List<Flight> find(Long flight_id, String departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights) {

        String sql =
        "SELECT f.id, f.departure_timestamp, f.flight_duration_minutes, f.flight_ticket_price, " +
        "adep.airport_code_name, ades.airport_code_name, " +
        "ldep.id, ldep.city, ldep.state, ldep.continent, " +
        "ldes.id, ldes.city, ldes.state, ldes.continent, " +
        "av.id, av.name, av.seat_rows, av.seat_columns, " +
        "d.id, d.discount_coefficient, d.valid_until_date " +
        "FROM flights f " +
        "LEFT JOIN airports adep ON adep.airport_code_name = f.airport_departure_code_name " +
        "LEFT JOIN airports ades ON ades.airport_code_name = f.airport_destination_code_name " +
        "LEFT JOIN airplanes av ON av.id = f.airplane_id " +
        "LEFT JOIN locations ldep ON ldep.id = adep.location_id " +
        "LEFT JOIN locations ldes ON ldes.id = ades.location_id " +
        "LEFT JOIN discounts_standard d ON d.id = f.discount_standard_id";

        ArrayList<Object> listaArgumenata = new ArrayList<Object>();
//
//        String sql = "SELECT id, name, surname, username, password, email, date_of_birth, registration_timestamp, admin, blocked FROM users ";
//
//        StringBuffer whereSql = new StringBuffer(" WHERE ");
//        boolean imaArgumenata = false;
//
//        if(name!=null) {
//            name = "%" + name + "%";
//            if(imaArgumenata)
//                whereSql.append(" AND ");
//            whereSql.append("name LIKE ?");
//            imaArgumenata = true;
//            listaArgumenata.add(name);
//        }
//
//        if(surname!=null) {
//            surname = "%" + surname + "%";
//            if(imaArgumenata)
//                whereSql.append(" AND ");
//            whereSql.append("surname LIKE ?");
//            imaArgumenata = true;
//            listaArgumenata.add(surname);
//        }
//
//        if(username!=null) {
//            username = "%" + username + "%";
//            if(imaArgumenata)
//                whereSql.append(" AND ");
//            whereSql.append("username LIKE ?");
//            imaArgumenata = true;
//            listaArgumenata.add(username);
//        }
//
//        if(email!=null) {
//            email = "%" + email + "%";
//            if(imaArgumenata)
//                whereSql.append(" AND ");
//            whereSql.append("email LIKE ?");
//            imaArgumenata = true;
//            listaArgumenata.add(email);
//        }
//
//        if(admin!=null) {
//            //vraća samo administratore ili sve korisnike sistema
//            String administratorSql = (admin)? "admin = 1": "admin >= 0";
//            if(imaArgumenata)
//                whereSql.append(" AND ");
//            whereSql.append(administratorSql);
//            imaArgumenata = true;
//        }
//
//        if(blocked!=null) {
//            String blockedSql = (blocked)? "blocked = 1": "blocked >= 0";
//            if(imaArgumenata)
//                whereSql.append(" AND ");
//            whereSql.append(blockedSql);
//            imaArgumenata = true;
//        }
//
//
//        if(imaArgumenata)
//            sql=sql + whereSql.toString()+" ORDER BY username";
//        else
//            sql=sql + " ORDER BY username";
        System.out.println(sql);

        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new FlightDAOImpl.FlightRowMapper());
    }



}
