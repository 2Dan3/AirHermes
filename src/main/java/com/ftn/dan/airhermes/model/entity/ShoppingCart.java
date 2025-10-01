package com.ftn.dan.airhermes.model.entity;

import java.util.ArrayList;

//public class ShoppingCart extends ArrayList<FlightTicket> {
public class ShoppingCart {

    private User user;
    private Long[] flightIDs;
    private Integer[] seatNumbers;
    private String[] passengerNames;
    private String[] passengerSurnames;
    private Integer[] passportNumbers;
    private Integer loyaltyPointsToUse;

    public ShoppingCart(User user, Long[] flightIDs, Integer[] seatNumbers, String[] passengerNames, String[] passengerSurnames, Integer[] passportNumbers, Integer loyaltyPointsToUse) {
        this.user = user;
        this.flightIDs = flightIDs;
        this.seatNumbers = seatNumbers;
        this.passengerNames = passengerNames;
        this.passengerSurnames = passengerSurnames;
        this.passportNumbers = passportNumbers;
        this.loyaltyPointsToUse = loyaltyPointsToUse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long[] getFlightIDs() {
        return flightIDs;
    }

    public void setFlightIDs(Long[] flightIDs) {
        this.flightIDs = flightIDs;
    }

    public Integer[] getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(Integer[] seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public String[] getPassengerNames() {
        return passengerNames;
    }

    public void setPassengerNames(String[] passengerNames) {
        this.passengerNames = passengerNames;
    }

    public String[] getPassengerSurnames() {
        return passengerSurnames;
    }

    public void setPassengerSurnames(String[] passengerSurnames) {
        this.passengerSurnames = passengerSurnames;
    }

    public Integer[] getPassportNumbers() {
        return passportNumbers;
    }

    public void setPassportNumbers(Integer[] passportNumbers) {
        this.passportNumbers = passportNumbers;
    }

    public Integer getLoyaltyPointsToUse() {
        return loyaltyPointsToUse;
    }

    public void setLoyaltyPointsToUse(Integer loyaltyPointsToUse) {
        this.loyaltyPointsToUse = loyaltyPointsToUse;
    }
}
