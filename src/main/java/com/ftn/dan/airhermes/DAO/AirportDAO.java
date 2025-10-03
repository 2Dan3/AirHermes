package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.Airport;

import java.util.List;

public interface AirportDAO {
    List<Airport> findAll();

    void save(String codeName, Long locationID);
}
