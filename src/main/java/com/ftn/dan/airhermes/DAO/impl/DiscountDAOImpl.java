package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.DiscountDAO;
import com.ftn.dan.airhermes.model.entity.Airplane;
import com.ftn.dan.airhermes.model.entity.DiscountStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class DiscountDAOImpl implements DiscountDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class DiscountRowMapper implements RowMapper<DiscountStandard> {

        @Override
        public DiscountStandard mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;

            Long id = rs.getLong(index++);
            Double coefficient = rs.getDouble(index++);
            Timestamp validUntil = rs.getTimestamp(index++);

            return new DiscountStandard(id, coefficient, validUntil);
        }
    }

    @Override
    public List<DiscountStandard> findAll() {
        final String sql =
                "SELECT d.id, d.discount_coefficient, d.valid_until_date " +
                "FROM discounts_standard d";
        return jdbcTemplate.query(sql, new DiscountRowMapper());
    }

    @Override
    public void save(DiscountStandard discount) {
        final String sql =
                "INSERT INTO discounts_standard (discount_coefficient, valid_until_date) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, discount.getDiscountCoefficient(), discount.getValidUntilDate());
    }
}
