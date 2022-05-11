package com.example.currencylist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    public void setDate(String date) {
        this.date = date;
    }
}
