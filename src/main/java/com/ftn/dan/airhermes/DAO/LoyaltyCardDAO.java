package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.LoyaltyCardCreationRequest;
import com.ftn.dan.airhermes.model.entity.User;

import java.util.List;

public interface LoyaltyCardDAO {
    LoyaltyCard findBy(User user);

    void update(LoyaltyCard loyaltyCard);

    String findCreationRequest(User user);

    void createRequest(LoyaltyCardCreationRequest request);

    boolean updateRequestStatus(LoyaltyCardCreationRequest request);

    void save(LoyaltyCard loyaltyCard);

    List<LoyaltyCardCreationRequest> findAll();

    void compensateReservationMaker(Flight flight);
}
