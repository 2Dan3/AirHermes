package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.FlightDAO;
import com.ftn.dan.airhermes.model.dto.FlightDTO;
import com.ftn.dan.airhermes.model.dto.ReportDTO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.FlightService;
import com.ftn.dan.airhermes.service.LoyaltyCardService;
import com.ftn.dan.airhermes.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DatabaseFlightService implements FlightService {
    @Autowired
    private FlightDAO flightDAO;
    @Autowired
    private LoyaltyCardService loyaltyCardService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public List<Flight> find(Long flight_id, Timestamp departureTimestamp, String departureAirportOrCityOrStateSearchTerm, String destinationAirportOrCityOrStateSearchTerm, Integer passengers, Boolean lookForSimilarTimingFlights, String sortAndDirection) {
        return flightDAO.find(flight_id, departureTimestamp, departureAirportOrCityOrStateSearchTerm, destinationAirportOrCityOrStateSearchTerm, passengers, lookForSimilarTimingFlights, sortAndDirection);
    }

    @Override
    public List<Flight> findAllBy(Long[] flightIds) {
        return flightDAO.findAllBy(flightIds);
    }

    @Override
    public List<FlightDTO> findFlightsFromWishlist(User user) {
        return flightDAO.findFlightsFromWishlist(user);
    }

    @Override
    public List<ReportDTO> findFlightsAndRevenueForInterval(Timestamp timestampMin, Timestamp timestampMax, String sortAndDirection) {
        return flightDAO.findFlightsAndRevenueForInterval(timestampMin, timestampMax, sortAndDirection);
    }

    @Transactional
    @Override
    public void cancelFlight(Flight flight, String reasonOfCancellation) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = flight.getDepartureTimestamp().toLocalDateTime();
        LocalDateTime deadline = departure.minusHours(1);

	    if (now.isBefore(deadline)) {
            flightDAO.cancelFlight(flight, reasonOfCancellation);
            loyaltyCardService.compensateReservationMaker(flight);
        }
        else {
            throw new Exception("It is too late to cancel the upcoming flight.");
        }
    }

    @Override
    public void save(Long flightID, String airportDepartureCodeName, String airportDestinationCodeName, Long airplaneID, LocalDateTime departureLocalDateTime, Integer flightDurationMinutes, Integer flightTicketPrice, Long discountStandardID) {
        flightDAO.save(flightID, airportDepartureCodeName, airportDestinationCodeName, airplaneID, departureLocalDateTime, flightDurationMinutes, flightTicketPrice, discountStandardID);
    }

    @Override
    public void updateFlight(Flight flight, String airportDepartureCodeName, String airportDestinationCodeName, Long airplaneID, LocalDateTime departureLocalDateTime, Integer flightDurationMinutes, Integer flightTicketPrice, Long discountStandardID) {
        flightDAO.update(flight.getId(), airportDepartureCodeName, airportDestinationCodeName, airplaneID, departureLocalDateTime, flightDurationMinutes, flightTicketPrice, discountStandardID);
    }

    @Override
    public void deleteFlight(Flight flight) {
        if (!reservationService.existsForFlight(flight.getId()))
            flightDAO.delete(flight.getId());
    }

    @Override
    public Integer parseMinutes(String formattedHoursAndMinutes) {
        String[] segments = formattedHoursAndMinutes.split(":");
        Integer hours = Integer.valueOf(segments[0]);
        Integer minutes = Integer.valueOf(segments[1]);
        return (hours * 60) + minutes;
    }
}
