package com.ftn.dan.airhermes.model.entity;

public class FlightTicket {
    private Long id;
    private Flight flight;
    private int seatNumber;
    private int flightTicketPrice;
    private String passengerNameSurname;
//    should consist of exactly 9 digits
    private int passportNumber;
}
