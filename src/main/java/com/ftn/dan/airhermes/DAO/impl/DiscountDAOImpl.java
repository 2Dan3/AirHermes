package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.DiscountDAO;
import com.ftn.dan.airhermes.model.entity.DiscountStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
    public Long save(DiscountStandard discount) {

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO discounts_standard (discount_coefficient, valid_until_date) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;

                preparedStatement.setDouble(index++, discount.getDiscountCoefficient());
                preparedStatement.setTimestamp(index++, discount.getValidUntilDate());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;

        if (success)
            return keyHolder.getKey().longValue();
        else
            return null;
    }

    @Override
    public DiscountStandard findByID(Long id) {
        final String sql =
                "SELECT d.id, d.discount_coefficient, d.valid_until_date " +
                        "FROM discounts_standard d " +
                        "WHERE d.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new DiscountRowMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            // case discount is not found
            return null;
        }
    }
}
