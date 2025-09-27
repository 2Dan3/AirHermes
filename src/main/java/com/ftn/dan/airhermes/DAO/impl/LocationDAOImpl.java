package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.LocationDAO;
import com.ftn.dan.airhermes.model.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDAOImpl implements LocationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Location location) {
        final String sql =
                "INSERT INTO locations (city, state, continent, image_path) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, location.getCity(), location.getState(), location.getContinent(), location.getImagePath());
    }

    @Override
    public List<Location> findAll() {
        final String sql =
                "SELECT id, city, state, continent, image_path " +
                "FROM locations";
        return jdbcTemplate.query(sql, new LocationRowMapper());
    }

    private class LocationRowMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String city = rs.getString(index++);
            String state = rs.getString(index++);
            String continent = rs.getString(index++);
            String imagePath = rs.getString(index++);

            return new Location(id, city, state, continent, imagePath);
        }
    }
}
