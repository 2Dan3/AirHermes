package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.Airplane;

import java.util.List;

public interface AirplaneService {
    List<Airplane> findAll();

    void save(Airplane airplane);
}
