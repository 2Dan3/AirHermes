package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.AirplaneDAO;
import com.ftn.dan.airhermes.model.entity.Airplane;
import com.ftn.dan.airhermes.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseAirplaneService implements AirplaneService {
    @Autowired
    private AirplaneDAO airplaneDAO;

    @Override
    public List<Airplane> findAll() {
        return airplaneDAO.findAll();
    }
}
