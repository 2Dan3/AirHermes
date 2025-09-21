package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;

import java.util.List;

public interface WishlistService {
    void addFlights(Long[] flightIDs, User loggedUser);

    void removeFlight(Long flightId, User loggedUser);

}
