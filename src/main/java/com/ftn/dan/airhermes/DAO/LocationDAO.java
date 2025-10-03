package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.Location;

import java.util.List;

public interface LocationDAO {
    void save(Location location);

    List<Location> findAll();
}
