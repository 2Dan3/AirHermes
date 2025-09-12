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

    public Flight(Long flight_uid, Date departure_timestamp, Integer flight_duration_minutes, Integer flight_ticket_price, Airplane airplane, Airport airportDeparture, Airport airportDestination, DiscountStandard discountStandard) {
        this.id = flight_uid;
        this.airportDeparture = airportDeparture;
        this.airportDestination = airportDestination;
        this.airplane = airplane;
        this.departureTimestamp = departure_timestamp;
        this.flightDurationMinutes = flight_duration_minutes;
        this.flightTicketPrice = flight_ticket_price;
        this.discountStandard = discountStandard;
    }

    @Override
    public int hashCode() {
        final int prime = 5;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return 5 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Flight other = (Flight) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airportDeparture=" + airportDeparture +
                ", airportDestination=" + airportDestination +
                ", airplane=" + airplane +
                ", departureTimestamp=" + departureTimestamp +
                ", flightDurationMinutes=" + flightDurationMinutes +
                ", flightTicketPrice=" + flightTicketPrice +
                ", discountStandard=" + discountStandard +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getAirportDeparture() {
        return airportDeparture;
    }

    public void setAirportDeparture(Airport airportDeparture) {
        this.airportDeparture = airportDeparture;
    }

    public Airport getAirportDestination() {
        return airportDestination;
    }

    public void setAirportDestination(Airport airportDestination) {
        this.airportDestination = airportDestination;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Date getDepartureTimestamp() {
        return departureTimestamp;
    }

    public void setDepartureTimestamp(Date departureTimestamp) {
        this.departureTimestamp = departureTimestamp;
    }

    public int getFlightDurationMinutes() {
        return flightDurationMinutes;
    }

    public void setFlightDurationMinutes(int flightDurationMinutes) {
        this.flightDurationMinutes = flightDurationMinutes;
    }

    public int getFlightTicketPrice() {
        return flightTicketPrice;
    }

    public void setFlightTicketPrice(int flightTicketPrice) {
        this.flightTicketPrice = flightTicketPrice;
    }

    public DiscountStandard getDiscountStandard() {
        return discountStandard;
    }

    public void setDiscountStandard(DiscountStandard discountStandard) {
        this.discountStandard = discountStandard;
    }
}
