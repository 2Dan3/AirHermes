package com.ftn.dan.airhermes.model.entity;

import com.ftn.dan.airhermes.model.enums.LoyaltyCardCreationRequestStatus;

public class LoyaltyCardCreationRequest {
//    private Long id;
    private User user;
    private LoyaltyCardCreationRequestStatus status = LoyaltyCardCreationRequestStatus.SENT;
}
