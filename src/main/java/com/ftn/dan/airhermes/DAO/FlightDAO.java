package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.FlightReservation;

import java.sql.Timestamp;
import java.util.List;

public interface FlightDAO {

    List<Flight> find(Long flight_id, Timestamp departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights);

    List<Flight> findAllBy(Long[] flightIds);
}
