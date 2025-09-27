package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.LocationDAO;
import com.ftn.dan.airhermes.model.entity.Location;
import com.ftn.dan.airhermes.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseLocationService implements LocationService {
    @Autowired
    private LocationDAO locationDAO;

    @Override
    public void save(Location location) {
        locationDAO.save(location);
    }

    @Override
    public List<Location> findAll() {
        return locationDAO.findAll();
    }
}
