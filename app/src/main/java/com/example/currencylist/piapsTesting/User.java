package com.example.currencylist.piapsTesting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {

    private JSONObject userData;

    private ErrorService errorService;

    public void setErrorService(ErrorService errorService){
        this.errorService = errorService;
    }

    private ArrayList<RegisteredUser> registeredUsers = new ArrayList<>();

    private String createRegisteredUser(String email,String password){
        RegisteredUser registeredUser = new RegisteredUser(email,password);
        return registeredUser.getEmail();
    }
    public void addRegisteredUser(String email,String password){
        registeredUsers.add(new RegisteredUser(email,password));
        System.out.print("Registered User added successfully");
    }

    public ArrayList<RegisteredUser> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setUserData(JSONObject jsonObject) {
        if (jsonObject == null){
            errorService.errorLog("JSONException");
        }
    }
}
