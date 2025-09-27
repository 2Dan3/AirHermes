package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.Airplane;

import java.util.List;

public interface AirplaneDAO {
    List<Airplane> findAll();

    void save(Airplane airplane);
}
