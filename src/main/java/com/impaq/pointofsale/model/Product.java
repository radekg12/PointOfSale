package com.impaq.pointofsale.model;

import java.math.BigDecimal;

public class Product {
    private String name;
    private Price price;

    public Product(String name, double price) {
        this.name = name;
        this.price = new Price(BigDecimal.valueOf(price));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public Product setPrice(Price price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return name + " \t" + price;
    }
}
