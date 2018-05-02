package com.impaq.pointofsale.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    public static final String SEPARATOR = "\n----------------\nTotal: \t";
    private List<Product> products;

    public Receipt() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Price getTotalPrice() {
        return products
                .stream()
                .map(Product::getPrice)
                .reduce(Price::add)
                .orElse(new Price(BigDecimal.ZERO));
    }

    @Override
    public String toString() {
        return products
                .stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n", "", SEPARATOR + getTotalPrice()));
    }
}
