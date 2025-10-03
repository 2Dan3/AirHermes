package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.FlightTicket;

import java.util.List;

public interface TicketDAO {
    int saveAll(Long reservationID, List<FlightTicket> tickets);
}
