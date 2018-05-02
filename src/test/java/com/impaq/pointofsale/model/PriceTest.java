package com.impaq.pointofsale.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class PriceTest {

    @Test
    public void shouldReturnCorrectSumOfTwoPrices() {
        //given
        Locale.setDefault(new Locale("pl", "PL"));
        Price price1 = new Price(5.55);
        Price price2 = new Price(1.20);
        //when
        Price sum = price1.add(price2);
        //then
        Assert.assertEquals("6,75 zł", sum.toString());
    }

    @Test
    public void shouldGetPriceForLocalePL() {
        //given
        Locale.setDefault(new Locale("pl", "PL"));
        Price price = new Price(5.55);
        //when
        String priceValue = price.toString();
        //then
        Assert.assertEquals("5,55 zł", priceValue);
    }

    @Test
    public void shouldGetPriceForLocaleUS() {
        //given
        Locale.setDefault(Locale.US);
        Price price = new Price(5.55);
        //when
        String priceValue = price.toString();
        //then
        Assert.assertEquals("$5.55", priceValue);
    }
}