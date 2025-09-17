package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.FlightTicket;

import java.util.List;

public interface TicketService {
    int saveAll(Long reservationID, List<FlightTicket> tickets);
}
