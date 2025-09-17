package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.ReservationDAO;
import com.ftn.dan.airhermes.model.entity.FlightReservation;
import com.ftn.dan.airhermes.service.ReservationService;
import com.ftn.dan.airhermes.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private TicketService ticketService;

    @Override
    public Long save(FlightReservation reservation, Long[] flightIDs) {
//        1) save to "flight_reservations"
//        2) save to "flight_flight_reservation"
        return reservationDAO.save(reservation, flightIDs);
    }

    @Override
    public int saveReservationAndTickets(FlightReservation reservation, Long[] flightIDs) {
        boolean success = false;
        Long reservationID = save(reservation, flightIDs);
        if (reservationID != null)
            success = ticketService.saveAll(reservationID, reservation.getIncludedFlightTickets()) > 0;

        return success ? 1 : 0;
    }

    private int updateTotalPrice(FlightReservation reservation, double price) {
        return reservationDAO.updateTotalPrice(reservation, price);
    }
}
