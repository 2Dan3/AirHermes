package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.FlightDAO;
import com.ftn.dan.airhermes.model.dto.FlightDTO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DatabaseFlightService implements FlightService {
    @Autowired
    private FlightDAO flightDAO;

    @Override
    public List<Flight> find(Long flight_id, Timestamp departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights) {
        return flightDAO.find(flight_id, departureTimestamp, departureAirportOrCityOrStateSearchTerm, destinationAirportOrCityOrStateSearchTerm, passengers, lookForSimilarTimingFlights);
    }

    @Override
    public List<Flight> findAllBy(Long[] flightIds) {
        return flightDAO.findAllBy(flightIds);
    }

    @Override
    public List<FlightDTO> findFlightsFromWishlist(User user) {
        return flightDAO.findFlightsFromWishlist(user);
    }
}
