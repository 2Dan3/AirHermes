package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> findAll();

    void save(String codeName, Long locationID);
}
