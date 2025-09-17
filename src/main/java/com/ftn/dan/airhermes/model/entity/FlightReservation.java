package com.ftn.dan.airhermes.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class FlightReservation {

    private Long id;
    private List<FlightTicket> includedFlightTickets;
    private Timestamp reservationCreationTimestamp;
    private int sumPriceOfFlightTickets;
    private User user;

//    public FlightReservation(User loggedUser){
//        this.reservationCreationTimestamp = Timestamp.valueOf(LocalDateTime.now());
//        this.sumPriceOfFlightTickets = 0;
//        this.user = loggedUser;
//    }

    public FlightReservation(User loggedUser, List<FlightTicket> newTickets, double totalPrice) {
        this.includedFlightTickets = newTickets;
        this.reservationCreationTimestamp = Timestamp.valueOf(LocalDateTime.now());
        this.sumPriceOfFlightTickets = (int) totalPrice;
        this.user = loggedUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FlightTicket> getIncludedFlightTickets() {
        return includedFlightTickets;
    }

    public void setIncludedFlightTickets(List<FlightTicket> includedFlightTickets) {
        this.includedFlightTickets = includedFlightTickets;
    }

    public Timestamp getReservationCreationTimestamp() {
        return reservationCreationTimestamp;
    }

    public void setReservationCreationTimestamp(Timestamp reservationCreationTimestamp) {
        this.reservationCreationTimestamp = reservationCreationTimestamp;
    }

    public int getSumPriceOfFlightTickets() {
        return sumPriceOfFlightTickets;
    }

    public void setSumPriceOfFlightTickets(int sumPriceOfFlightTickets) {
        this.sumPriceOfFlightTickets = sumPriceOfFlightTickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
