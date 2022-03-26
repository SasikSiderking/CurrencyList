package com.example.currencylist;

public class Currency {
    private final String charCode;
    private final String nominal;
    private String name;
    private final String value;
    private final String previous;

    public Currency(String charCode, String nominal, String name, String value, String previous) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previous = previous;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getPrevious() {
        return previous;
    }
}
