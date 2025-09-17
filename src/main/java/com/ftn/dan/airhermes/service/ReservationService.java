package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.FlightReservation;

public interface ReservationService {
    Long save(FlightReservation reservation, Long[] flightIDs);

    int saveReservationAndTickets(FlightReservation reservation, Long[] flightIDs);
}
