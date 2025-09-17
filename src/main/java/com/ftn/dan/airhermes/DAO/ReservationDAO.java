package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.FlightReservation;

public interface ReservationDAO {
    int updateTotalPrice(FlightReservation reservation, double price);

    Long save(FlightReservation reservation, Long[] flightIDs);
}
