package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.dto.ReservationDTO;
import com.ftn.dan.airhermes.model.entity.FlightReservation;

import java.util.List;

public interface ReservationService {
    Long save(FlightReservation reservation, Long[] flightIDs);

    int saveReservationAndTickets(FlightReservation reservation, Long[] flightIDs);

    List<ReservationDTO> findAllWithFlightsByUserId(Long userId);

    boolean existsForFlight(Long flightId);

    boolean notEnoughSeats(int seats, Long flight1Id);
}
