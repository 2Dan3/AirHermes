package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.LoyaltyCardDAO;
import com.ftn.dan.airhermes.DAO.UserDAO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.LoyaltyCardCreationRequest;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.model.enums.LoyaltyCardCreationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    private class RequestRowMapper implements RowMapper<LoyaltyCardCreationRequest> {

        @Override
        public LoyaltyCardCreationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long userID = rs.getLong(index++);
            String status = rs.getString(index++);

            User requester = userDAO.findByID(userID);

            LoyaltyCardCreationRequest request = new LoyaltyCardCreationRequest(requester, LoyaltyCardCreationRequestStatus.valueOf(status));
            return request;
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

    @Override
    public String findCreationRequest(User user) {
        try {
            String sql = "SELECT status FROM loyalty_card_creation_requests WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, String.class, user.getId());
        } catch (EmptyResultDataAccessException ex) {
            // case request is not found
            return null;
        }
    }

    @Override
    public void createRequest(LoyaltyCardCreationRequest request) {
        String sql = "INSERT INTO loyalty_card_creation_requests (user_id, status) VALUES (?, ?)";
        jdbcTemplate.update(sql, request.getUser().getId(), request.getStatus().toString());
    }

    @Override
    public boolean updateRequestStatus(LoyaltyCardCreationRequest request) {
        String sql = "UPDATE loyalty_card_creation_requests SET status = ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, request.getStatus().toString(), request.getUser().getId()) == 1;
    }

    @Override
    public void save(LoyaltyCard loyaltyCard) {
        String sql = "INSERT INTO loyalty_cards (spent_money_unconverted_to_points, points_collected, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, loyaltyCard.getSpentMoneyUnconvertedToPoints(), loyaltyCard.getPointsCollected(), loyaltyCard.getUserOfCard().getId());
    }

    @Override
    public List<LoyaltyCardCreationRequest> findAll() {
        String sql = "SELECT user_id, status FROM loyalty_card_creation_requests";
        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    @Override
    public void compensateReservationMaker(Flight flight) {
        final String sql =
                "UPDATE loyalty_cards " +
                "SET points_collected = points_collected + 5 " +
                "WHERE user_id IN (" +
                "  SELECT u.id " +
                "  FROM users u" +
                "  INNER JOIN flight_reservations r ON u.id = r.user_id " +
                "  INNER JOIN flight_flight_reservation fr ON r.id = fr.flight_reservation_id " +
                "  WHERE fr.flight_id = ?)";
        jdbcTemplate.update(sql, flight.getId());
    }
}