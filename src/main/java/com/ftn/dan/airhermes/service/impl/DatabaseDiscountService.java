package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.DiscountDAO;
import com.ftn.dan.airhermes.model.entity.DiscountStandard;
import com.ftn.dan.airhermes.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Long save(DiscountStandard discount) {
        return discountDAO.save(discount);
    }

    @Override
    public DiscountStandard findByID(Long id) {
        return discountDAO.findByID(id);
    }

    @Override
    public boolean isExpired(Long discountId) {
        if (discountId == null)
            return false;

        DiscountStandard foundDiscount = findByID(discountId);

        return foundDiscount == null || foundDiscount.getValidUntilDate().toLocalDateTime().isBefore(LocalDateTime.now());
    }

    @Override
    public List<DiscountStandard> findAllNonExpired() {
        return discountDAO.findAllNonExpired();
    }
}
