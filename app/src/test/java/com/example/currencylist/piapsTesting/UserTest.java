package com.example.currencylist.piapsTesting;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UserTest {
    User user = new User();

    @Test
    public void addRegisteredUser() {

        String consoleOutput;
        PrintStream originalOut = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(128);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            user.addRegisteredUser("Abobus@mail.ru","123");
            capture.flush();
            consoleOutput = outputStream.toString();
            System.setOut(originalOut);

            Assert.assertEquals("Registered User added successfully", consoleOutput);
    }

    @Test
    public void getRegisteredUsers() {
        Assert.assertTrue(user.getRegisteredUsers() instanceof ArrayList);
    }

    @Test
    public void testPrivateMethod() {
        try{
        Method method = User.class.getDeclaredMethod("createRegisteredUser", String.class, String.class);
        method.setAccessible(true);
        Assert.assertEquals(method.invoke(user,"Abobus@mail.ru","123").toString(),"Abobus@mail.ru");}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSetUserData(){

        MockErrorService mockErrorService = new MockErrorService();
        user.setErrorService(mockErrorService);
        user.setUserData(null);
        Assert.assertEquals("JSONException",mockErrorService.error);
    }

}