package com.impaq.pointofsale.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReceiptTest {
    private Product product1;
    private Product product2;
    private Receipt receipt;

    @Before
    public void setUp() throws Exception {
        product1 = new Product("Book", 25.55);
        product2 = new Product("Pencil", 3.20);
        receipt = new Receipt();
    }


    @Test
    public void shouldGetTotalPriceForAllItems() {
        //given
        receipt.addProduct(product1);
        receipt.addProduct(product2);
        //when
        String totalPrice = receipt.getTotalPrice().toString();
        //then
        Assert.assertEquals(
                product1.getPrice().add(product2.getPrice()).toString(), totalPrice);
    }


    @Test
    public void shouldGetZeroForEmptyListOfProducts() {
        //when
        String totalPrice = receipt.getTotalPrice().toString();
        //then
        Assert.assertEquals(new Price(0).toString(), totalPrice);
    }

    @Test
    public void shouldGetCorrectForNotEmptyListOfProducts() {
        //given
        receipt.addProduct(product1);
        receipt.addProduct(product2);
        //when
        String productsList = receipt.toString();
        //then
        Assert.assertEquals(
                product1.toString() + "\n" + product2.toString() + Receipt.SEPARATOR
                        + product1.getPrice().add(product2.getPrice()), productsList);
    }

    @Test
    public void shouldGetCorrectForEmptyListOfProducts() {
        //when
        String productsList = receipt.toString();
        //then
        Assert.assertEquals(Receipt.SEPARATOR + new Price(0), productsList);
    }
}