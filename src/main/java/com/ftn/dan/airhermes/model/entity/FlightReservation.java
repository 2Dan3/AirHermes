package com.ftn.dan.airhermes.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class FlightReservation {

    private List<FlightTicket> includedFlightTickets;
    private Timestamp reservationCreationTimestamp;
    private int sumPriceOfFlightTickets;
//  todo ?  private User user;
}
