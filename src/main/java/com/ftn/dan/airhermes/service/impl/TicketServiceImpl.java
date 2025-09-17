package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.TicketDAO;
import com.ftn.dan.airhermes.model.entity.FlightTicket;
import com.ftn.dan.airhermes.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public int saveAll(Long reservationID, List<FlightTicket> tickets) {
        return ticketDAO.saveAll(reservationID, tickets);
    }
}
