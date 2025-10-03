package com.ftn.dan.airhermes.model.dto;

import java.sql.Timestamp;

public class ReservationDTO {
    private Long flightId;
    private String airportDepartureCodeName;
    private String airportDestinationCodeName;
    private Timestamp flightDepartureTimestamp;
    private Long reservationId;
    private Timestamp reservationCreationTimestamp;
    private Double reservationSumPriceOfAllTickets;
    private boolean cancelled;

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ReservationDTO(Long flightId, String airportDepartureCodeName, String airportDestinationCodeName, Timestamp flightDepartureTimestamp, Long reservationId, Timestamp reservationCreationTimestamp, Double reservationSumPriceOfAllTickets, Boolean cancelled) {
        this.flightId = flightId;
        this.airportDepartureCodeName = airportDepartureCodeName;
        this.airportDestinationCodeName = airportDestinationCodeName;
        this.flightDepartureTimestamp = flightDepartureTimestamp;
        this.reservationId = reservationId;
        this.reservationCreationTimestamp = reservationCreationTimestamp;
        this.reservationSumPriceOfAllTickets = reservationSumPriceOfAllTickets;
        this.cancelled = cancelled != null && cancelled;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getAirportDepartureCodeName() {
        return airportDepartureCodeName;
    }

    public void setAirportDepartureCodeName(String airportDepartureCodeName) {
        this.airportDepartureCodeName = airportDepartureCodeName;
    }

    public String getAirportDestinationCodeName() {
        return airportDestinationCodeName;
    }

    public void setAirportDestinationCodeName(String airportDestinationCodeName) {
        this.airportDestinationCodeName = airportDestinationCodeName;
    }

    public Timestamp getFlightDepartureTimestamp() {
        return flightDepartureTimestamp;
    }

    public void setFlightDepartureTimestamp(Timestamp flightDepartureTimestamp) {
        this.flightDepartureTimestamp = flightDepartureTimestamp;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Timestamp getReservationCreationTimestamp() {
        return reservationCreationTimestamp;
    }

    public void setReservationCreationTimestamp(Timestamp reservationCreationTimestamp) {
        this.reservationCreationTimestamp = reservationCreationTimestamp;
    }

    public Double getReservationSumPriceOfAllTickets() {
        return reservationSumPriceOfAllTickets;
    }

    public void setReservationSumPriceOfAllTickets(Double reservationSumPriceOfAllTickets) {
        this.reservationSumPriceOfAllTickets = reservationSumPriceOfAllTickets;
    }
}
