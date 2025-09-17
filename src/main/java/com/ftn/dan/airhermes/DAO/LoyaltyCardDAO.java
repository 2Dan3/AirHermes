package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.LoyaltyCard;
import com.ftn.dan.airhermes.model.entity.User;

public interface LoyaltyCardDAO {
    LoyaltyCard findBy(User user);

    void update(LoyaltyCard loyaltyCard);
}
