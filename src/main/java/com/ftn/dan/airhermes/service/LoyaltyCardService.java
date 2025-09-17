package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.User;

public interface LoyaltyCardService {
    LoyaltyCard findBy(User loggedUser);

    void update(LoyaltyCard loyaltyCard);
}
