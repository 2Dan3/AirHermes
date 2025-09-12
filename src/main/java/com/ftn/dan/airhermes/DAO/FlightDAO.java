package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.Flight;

import java.util.List;

public interface FlightDAO {

    List<Flight> find(Long flight_id, String departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights);
}
