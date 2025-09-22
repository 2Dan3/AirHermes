package com.ftn.dan.airhermes.model.entity;

import com.ftn.dan.airhermes.model.enums.LoyaltyCardCreationRequestStatus;

public class LoyaltyCardCreationRequest {
//    private Long id;
    private User user;
    private LoyaltyCardCreationRequestStatus status;

    public LoyaltyCardCreationRequest(User user, LoyaltyCardCreationRequestStatus status) {
        this.user = user;
        this.status = status;
    }

    public LoyaltyCardCreationRequest(User user) {
        this.user = user;
        this.status = LoyaltyCardCreationRequestStatus.SENT;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoyaltyCardCreationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(LoyaltyCardCreationRequestStatus status) {
        this.status = status;
    }
}
