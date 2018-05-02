package com.impaq.pointofsale.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Price {
    private BigDecimal value;
    private NumberFormat priceFormat;


    public Price(BigDecimal value) {
        this.value = value;
        priceFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);
    }

    public Price(double value) {
        this(BigDecimal.valueOf(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    public Price setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public Price add(Price price) {
        return new Price(this.value.add(price.value));
    }

    @Override
    public String toString() {
        return priceFormat.format(value);
    }
}
