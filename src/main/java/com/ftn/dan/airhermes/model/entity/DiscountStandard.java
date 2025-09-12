package com.ftn.dan.airhermes.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class DiscountStandard {
    private Long id;
//   todo can be 0.01 to 0.99
    private double discountCoefficient;
    private Timestamp validUntilDate;

    public DiscountStandard(Long discount_id, Double discount_coefficient, Timestamp valid_until_date) {
        this.id = discount_id;
        this.discountCoefficient = discount_coefficient;
        this.validUntilDate = valid_until_date;
    }

    @Override
    public int hashCode() {
        final int prime = 71;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return 71 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiscountStandard other = (DiscountStandard) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DiscountStandard{" +
                "id=" + id +
                ", discountCoefficient=" + discountCoefficient +
                ", validUntilDate=" + validUntilDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscountCoefficient() {
        return discountCoefficient;
    }

    public void setDiscountCoefficient(double discountCoefficient) {
        this.discountCoefficient = discountCoefficient;
    }

    public Timestamp getValidUntilDate() {
        return validUntilDate;
    }

    public void setValidUntilDate(Timestamp validUntilDate) {
        this.validUntilDate = validUntilDate;
    }
}
