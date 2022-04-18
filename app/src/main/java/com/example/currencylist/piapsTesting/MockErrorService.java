package com.example.currencylist.piapsTesting;

public class MockErrorService implements ErrorService {
    public String error;

    public void errorLog(String error){
        this.error = error;
    }
}
