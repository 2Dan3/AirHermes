package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.dto.ReservationDTO;
import com.ftn.dan.airhermes.model.entity.FlightReservation;

import java.util.List;

public interface ReservationDAO {
    int updateTotalPrice(FlightReservation reservation, double price);

    Long save(FlightReservation reservation, Long[] flightIDs);

    List<ReservationDTO> findAllWithFlightsByUserId(Long userId);

    boolean existsReservationForFlight(Long flightID);

    boolean notEnoughSeats(int seats, Long flight1Id);
}
