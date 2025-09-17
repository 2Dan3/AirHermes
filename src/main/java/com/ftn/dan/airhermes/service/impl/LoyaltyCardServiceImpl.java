package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.LoyaltyCardDAO;
import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.LoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
