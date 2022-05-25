package com.example.currencylist;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import Data.CurrencyAppDatabase;

public class CurrencyTest {
    private CurrencyAppDatabase currencyAppDatabase;
//Currency currency = currencyAppDatabase.getCurrencyDAO().getCurrency("R01010","2022-05-25T11:30:00+03:00");
    Currency currency = new Currency("AUD",1,"Австралийский доллар",40.3511,41.398,"2022-05-25T11:00:00+03:00");
    @Test
    public void getNumDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        LocalDate localDate = LocalDate.parse(currency.getDate(),formatter);
        Date date = java.sql.Date.valueOf(String.valueOf(localDate));
        System.out.println(date.toString());
        assertEquals(date.toString(),"2022-05-25");
    }
}