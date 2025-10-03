package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.LoyaltyCardDAO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.LoyaltyCardCreationRequest;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.model.enums.LoyaltyCardCreationRequestStatus;
import com.ftn.dan.airhermes.service.LoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyCardServiceImpl implements LoyaltyCardService {

    @Autowired
    private LoyaltyCardDAO loyaltyCardDAO;

    @Override
    public LoyaltyCard findBy(User loggedUser) {
        return loggedUser == null ? null : loyaltyCardDAO.findBy(loggedUser);
    }

    @Override
    public void update(LoyaltyCard loyaltyCard) {
        loyaltyCardDAO.update(loyaltyCard);
    }

    @Override
    public String findCreationRequest(User user) {
        return loyaltyCardDAO.findCreationRequest(user);
    }

    @Override
    public void sendOrResendRequest(User user) {

        LoyaltyCardCreationRequest request = new LoyaltyCardCreationRequest(user);
        try {
            loyaltyCardDAO.createRequest(request);
        }catch (DuplicateKeyException e) {
            loyaltyCardDAO.updateRequestStatus(request);
        }
    }

    @Override
    public void updateStatus(LoyaltyCardCreationRequest request) {
        boolean successful = loyaltyCardDAO.updateRequestStatus(request);

        if (successful && request.getStatus().equals(LoyaltyCardCreationRequestStatus.APPROVED)) {
            loyaltyCardDAO.save(new LoyaltyCard(null, 0.0, 5, request.getUser()));
        }
    }

    @Override
    public List<LoyaltyCardCreationRequest> findAll() {
        return loyaltyCardDAO.findAll();
    }

    @Override
    public void compensateReservationMaker(Flight flight) {
        loyaltyCardDAO.compensateReservationMaker(flight);
    }
}
