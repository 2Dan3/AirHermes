package com.ftn.dan.airhermes.model.dto;

import com.ftn.dan.airhermes.model.entity.Airplane;
import com.ftn.dan.airhermes.model.entity.Airport;
import com.ftn.dan.airhermes.model.entity.DiscountStandard;

import java.sql.Timestamp;

public class FlightDTO {
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

    public Timestamp getDepartureTimestamp() {
        return departureTimestamp;
    }

    public void setDepartureTimestamp(Timestamp departureTimestamp) {
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

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    private Long id;
    private Airport airportDeparture;
    private Airport airportDestination;
    private Airplane airplane;
    private Timestamp departureTimestamp;
    private int flightDurationMinutes;
    private int flightTicketPrice;
    private DiscountStandard discountStandard;
    private boolean soldOut;

    public FlightDTO(Long flight_uid, Timestamp departure_timestamp, Integer flight_duration_minutes, Integer flight_ticket_price, Airplane airplane, Airport airportDeparture, Airport airportDestination, DiscountStandard discountStandard, boolean soldOut) {
        this.id = flight_uid;
        this.airportDeparture = airportDeparture;
        this.airportDestination = airportDestination;
        this.airplane = airplane;
        this.departureTimestamp = departure_timestamp;
        this.flightDurationMinutes = flight_duration_minutes;
        this.flightTicketPrice = flight_ticket_price;
        this.discountStandard = discountStandard;
        this.soldOut = soldOut;
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return 3 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FlightDTO other = (FlightDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
