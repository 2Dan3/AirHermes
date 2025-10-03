package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.AirportDAO;
import com.ftn.dan.airhermes.model.entity.Airport;
import com.ftn.dan.airhermes.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseAirportService implements AirportService {
    @Autowired
    private AirportDAO airportDAO;

    @Override
    public List<Airport> findAll() {
        return airportDAO.findAll();
    }

    @Override
    public void save(String codeName, Long locationID) {
        airportDAO.save(codeName, locationID);
    }
}
