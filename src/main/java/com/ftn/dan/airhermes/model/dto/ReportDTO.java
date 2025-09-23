package com.ftn.dan.airhermes.model.dto;

import java.sql.Timestamp;

public class ReportDTO {
    private Long flightId;
    private Timestamp departureTimestamp;
    private Long seatsTotal;
    private Long seatsSold;
    private Double flightRevenue;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Timestamp getDepartureTimestamp() {
        return departureTimestamp;
    }

    public void setDepartureTimestamp(Timestamp departureTimestamp) {
        this.departureTimestamp = departureTimestamp;
    }

    public Long getSeatsTotal() {
        return seatsTotal;
    }

    public void setSeatsTotal(Long seatsTotal) {
        this.seatsTotal = seatsTotal;
    }

    public Long getSeatsSold() {
        return seatsSold;
    }

    public void setSeatsSold(Long seatsSold) {
        this.seatsSold = seatsSold;
    }

    public Double getFlightRevenue() {
        return flightRevenue;
    }

    public void setFlightRevenue(Double flightRevenue) {
        this.flightRevenue = flightRevenue;
    }

    public ReportDTO(Long flight_uid, Timestamp departure_timestamp, Long flight_seats_total, Long flight_tickets_sold, Double total_flight_revenue) {
        this.flightId = flight_uid;
        this.departureTimestamp = departure_timestamp;
        this.seatsTotal = flight_seats_total;
        this.seatsSold = flight_tickets_sold;
        this.flightRevenue = total_flight_revenue;
    }
}
