package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.Location;

import java.util.List;

public interface LocationService {
    void save(Location location);

    List<Location> findAll();
}
