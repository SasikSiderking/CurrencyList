package com.example.currencylist;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity(tableName="currencies", primaryKeys = {"id","date"})
public class Currency {
    @NonNull
    @ColumnInfo
    private String id;
    @ColumnInfo
    private String charCode;
    @ColumnInfo
    private int nominal;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private double value;
    @ColumnInfo
    private double previousValue;
    @NonNull
    @ColumnInfo
    private String date;

    @Ignore
    public Currency() {
    }

    public Currency(@NonNull String id, String charCode, int nominal, String name, double value, double previousValue, String date) {
        this.id = id;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previousValue = previousValue;
        this.date = date;
    }

    @Ignore
    public Currency(String charCode, int nominal, String name, double value, double previousValue, String date) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previousValue = previousValue;
        this.date = date;
    }

    @Ignore
    public Currency(String charCode, int nominal, String name, double value, double previousValue) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previousValue = previousValue;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public double getPreviousValue() {
        return previousValue;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public float getNumDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        LocalDate localDate = LocalDate.parse(date,formatter);
        Date date = java.sql.Date.valueOf(String.valueOf(localDate));
//        System.out.println(date.toString());
        return (float) date.getTime();
    }

    public void setDate(String date) {
        this.date = date;
    }
}
