package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.WishlistDAO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseWishlistService implements WishlistService {

    @Autowired
    private WishlistDAO wishlistDAO;

    @Override
    public void addFlights(Long[] flightIDs, User loggedUser) {
        try {
            wishlistDAO.addFlights(flightIDs, loggedUser);
        }catch (DuplicateKeyException ex) {
            return;
        }
    }

    @Override
    public void removeFlight(Long flightId, User loggedUser) {
        wishlistDAO.removeFlight(flightId, loggedUser);
    }

}
