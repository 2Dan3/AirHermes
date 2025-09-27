package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.DiscountDAO;
import com.ftn.dan.airhermes.model.entity.DiscountStandard;
import com.ftn.dan.airhermes.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseDiscountService implements DiscountService {
    @Autowired
    private DiscountDAO discountDAO;

    @Override
    public List<DiscountStandard> findAll() {
        return discountDAO.findAll();
    }

    @Override
    public void save(DiscountStandard discount) {
        discountDAO.save(discount);
    }
}
