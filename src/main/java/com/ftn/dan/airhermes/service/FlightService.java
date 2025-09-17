package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.Flight;

import java.sql.Timestamp;
import java.util.List;

public interface FlightService {
    List<Flight> find(Long flight_id, Timestamp departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights);

    List<Flight> findAllBy(Long[] flightIds);
}
