package com.ftn.dan.airhermes.model.entity;

import java.util.Date;

public class Flight {
    private Long id;
    private Airport airportDeparture;
    private Airport airportDestination;
    private Airplane airplane;
    private Date departureTimestamp;
    private int flightDurationMinutes;
    private int flightTicketPrice;
    private DiscountStandard discountStandard;
}
