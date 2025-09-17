package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.LoyaltyCardDAO;
import com.ftn.dan.airhermes.DAO.UserDAO;
import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LoyaltyCardDAOImpl implements LoyaltyCardDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDAO userDAO;

    private class LoyaltyCardRowMapper implements RowMapper<LoyaltyCard> {

        @Override
        public LoyaltyCard mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            Double spentMoneyUnconvertedToPoints = rs.getDouble(index++);
            Integer pointsCollected = rs.getInt(index++);
            Long userID = rs.getLong(index++);

            User cardOwner = userDAO.findByID(userID);

            LoyaltyCard loyaltyCard = new LoyaltyCard(id, spentMoneyUnconvertedToPoints, pointsCollected, cardOwner);
            return loyaltyCard;
        }

    }


    @Override
    public LoyaltyCard findBy(User user) {
        try {
            String sql = "SELECT id, spent_money_unconverted_to_points, points_collected, user_id FROM loyalty_cards WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, new LoyaltyCardRowMapper(), user.getId());
        } catch (EmptyResultDataAccessException ex) {
            // case card is not found
            return null;
        }
    }

    @Override
    public void update(LoyaltyCard loyaltyCard) {
        int success = 0;

        String sql = "UPDATE loyalty_cards SET spent_money_unconverted_to_points = ?, points_collected = ? WHERE id = ?";
        success = jdbcTemplate.update(sql, loyaltyCard.getSpentMoneyUnconvertedToPoints(), loyaltyCard.getPointsCollected(), loyaltyCard.getId());

//        return success;
    }
}
