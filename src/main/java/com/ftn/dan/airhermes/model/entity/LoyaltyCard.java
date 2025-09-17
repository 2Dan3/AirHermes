package com.ftn.dan.airhermes.model.entity;

public class LoyaltyCard {
    private Long id;
    private double spentMoneyUnconvertedToPoints;
    private int pointsCollected = 5;
    private User userOfCard;

    public LoyaltyCard(Long id, Double spentMoneyUnconvertedToPoints, Integer pointsCollected, User cardOwner) {
        this.id = id;
        this.spentMoneyUnconvertedToPoints = spentMoneyUnconvertedToPoints;
        this.pointsCollected = pointsCollected;
        this.userOfCard = cardOwner;
    }

    @Override
    public String toString() {
        return "LoyaltyCard{" +
                "id=" + id +
                ", spentMoneyUnconvertedToPoints=" + spentMoneyUnconvertedToPoints +
                ", pointsCollected=" + pointsCollected +
                ", userOfCard=" + userOfCard +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSpentMoneyUnconvertedToPoints() {
        return spentMoneyUnconvertedToPoints;
    }

    public void setSpentMoneyUnconvertedToPoints(double spentMoneyUnconvertedToPoints) {
        this.spentMoneyUnconvertedToPoints = spentMoneyUnconvertedToPoints;
    }

    public int getPointsCollected() {
        return pointsCollected;
    }

    public void setPointsCollected(int pointsCollected) {
        this.pointsCollected = pointsCollected;
    }

    public User getUserOfCard() {
        return userOfCard;
    }

    public void setUserOfCard(User userOfCard) {
        this.userOfCard = userOfCard;
    }
}
