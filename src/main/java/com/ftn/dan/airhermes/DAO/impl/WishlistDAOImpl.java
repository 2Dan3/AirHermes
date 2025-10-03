package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.WishlistDAO;
import com.ftn.dan.airhermes.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDAOImpl implements WishlistDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addFlights(Long[] flightIDs, User loggedUser) throws DuplicateKeyException {
        boolean success = true;

        String sql = "INSERT INTO wish_list_of_flights (flight_id, user_id) VALUES (?, ?)";
        for (Long flightID : flightIDs) {
            success = success && jdbcTemplate.update(sql, flightID, loggedUser.getId()) == 1;
        }
//        return success;
    }

    @Override
    public void removeFlight(Long flightId, User loggedUser) {
        String sql = "DELETE FROM wish_list_of_flights WHERE flight_id = ? AND user_id = ?";
        boolean success = jdbcTemplate.update(sql, flightId, loggedUser.getId()) == 1;
//        return success;
    }


}
