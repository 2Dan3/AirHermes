package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.DiscountStandard;

import java.util.List;

public interface DiscountDAO {
    List<DiscountStandard> findAll();
}
