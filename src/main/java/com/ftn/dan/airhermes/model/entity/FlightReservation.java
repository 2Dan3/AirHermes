package com.ftn.dan.airhermes.model.entity;

import java.util.Date;
import java.util.List;

public class FlightReservation {

    private List<FlightTicket> includedFlightTickets;
    private Date reservationCreationTimestamp;
    private int sumPriceOfFlightTickets;
//  todo ?  private User user;
}
