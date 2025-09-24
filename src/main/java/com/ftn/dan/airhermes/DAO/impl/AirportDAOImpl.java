package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.AirportDAO;
import com.ftn.dan.airhermes.model.entity.Airport;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AirportDAOImpl implements AirportDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class AirportRowMapper implements RowMapper<Airport> {

        @Override
        public Airport mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;

            String codeName = rs.getString(index++);
            Long locationId = rs.getLong(index++);
            String city = rs.getString(index++);
            String state = rs.getString(index++);
            String continent = rs.getString(index++);
            String imagePath = rs.getString(index++);

            Location location = new Location(locationId, city, state, continent, imagePath);

            return new Airport(codeName, location);
        }
    }

    @Override
    public List<Airport> findAll() {
        final String sql =
                "SELECT a.airport_code_name, a.location_id, l.city, l.state, l.continent, l.image_path " +
                "FROM airports a " +
                "JOIN locations l ON l.id = a.location_id";
        return jdbcTemplate.query(sql, new AirportRowMapper());
    }
}
