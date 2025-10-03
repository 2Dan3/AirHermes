package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.LoyaltyCardCreationRequest;
import com.ftn.dan.airhermes.model.entity.User;

import java.util.List;

public interface LoyaltyCardService {
    LoyaltyCard findBy(User loggedUser);

    void update(LoyaltyCard loyaltyCard);

    String findCreationRequest(User user);

    void sendOrResendRequest(User user);

    void updateStatus(LoyaltyCardCreationRequest request);

    List<LoyaltyCardCreationRequest> findAll();

    void compensateReservationMaker(Flight flight);
}
