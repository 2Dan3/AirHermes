package com.ftn.dan.airhermes.model.entity;

public class FlightTicket {
    private Long id;
    private Flight flight;
    private FlightReservation reservation;
    private int seatNumber;
    private int flightTicketPrice;
    private String passengerName;
    private String passengerSurname;
//    should consist of exactly 9 digits
    private int passportNumber;

    public FlightTicket(Flight flight, Integer seatNumber, String passengerName, String passengerSurname, Integer passportNumber, FlightReservation reservation, double price) {
        this.flight = flight;
        this.seatNumber = seatNumber;
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
        this.passportNumber = passportNumber;
        this.reservation = reservation;
        this.flightTicketPrice = (int) price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public FlightReservation getReservation() {
        return reservation;
    }

    public void setReservation(FlightReservation reservation) {
        this.reservation = reservation;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getFlightTicketPrice() {
        return flightTicketPrice;
    }

    public void setFlightTicketPrice(int flightTicketPrice) {
        this.flightTicketPrice = flightTicketPrice;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerSurname() {
        return passengerSurname;
    }

    public void setPassengerSurname(String passengerSurname) {
        this.passengerSurname = passengerSurname;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }
}
