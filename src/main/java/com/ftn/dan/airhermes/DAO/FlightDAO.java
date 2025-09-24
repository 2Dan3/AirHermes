package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.dto.FlightDTO;
import com.ftn.dan.airhermes.model.dto.ReportDTO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightDAO {

    List<Flight> find(Long flight_id, Timestamp departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights);

    List<Flight> findAllBy(Long[] flightIds);

    List<FlightDTO> findFlightsFromWishlist(User user);

    List<ReportDTO> findFlightsAndRevenueForInterval(Timestamp timestampMin, Timestamp timestampMax);

    void cancelFlight(Flight flight, String reasonOfCancellation);

    void save(Long flightID, String airportDepartureCodeName, String airportDestinationCodeName, Long airplaneID, LocalDateTime departureLocalDateTime, Integer flightDurationMinutes, Integer flightTicketPrice, Long discountStandardID);

    void update(Long flightID, String airportDepartureCodeName, String airportDestinationCodeName, Long airplaneID, LocalDateTime departureLocalDateTime, Integer flightDurationMinutes, Integer flightTicketPrice, Long discountStandardID);

    void delete(Long id);
}
