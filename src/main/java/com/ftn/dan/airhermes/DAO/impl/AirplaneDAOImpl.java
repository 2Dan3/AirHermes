package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.AirplaneDAO;
import com.ftn.dan.airhermes.model.entity.Airplane;
import com.ftn.dan.airhermes.model.entity.Airport;
import com.ftn.dan.airhermes.model.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AirplaneDAOImpl implements AirplaneDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class AirplaneRowMapper implements RowMapper<Airplane> {

        @Override
        public Airplane mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;

            Long id = rs.getLong(index++);
            String name = rs.getString(index++);
            Integer seatRows = rs.getInt(index++);
            Integer seatColumns = rs.getInt(index++);

            return new Airplane(id, name, seatRows, seatColumns);
        }
    }

    @Override
    public List<Airplane> findAll() {
        final String sql =
                "SELECT av.id, av.name, av.seat_rows, av.seat_columns " +
                        "FROM airplanes av";
        return jdbcTemplate.query(sql, new AirplaneRowMapper());
    }
}
