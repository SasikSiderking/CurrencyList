package com.example.currencylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import Data.CurrencyAppDatabase;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        ProgressBar progressBar = findViewById(R.id.progress_circular);

        String url = "https://www.cbr-xml-daily.ru/daily_json.js";

        currencyAppDatabase = Room.databaseBuilder(getApplicationContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        saveCurrencies(url,progressBar);
        showNumberOfRecordsInDb();
    }

    private void getCurrencies(String url, VolleyCallback callback) {
        requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                {
                    callback.onSuccessResponse(response);
                }
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    public void saveCurrencies(String url,ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getCurrencies(url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject response) {
                try {
                    JSONObject valute = response.getJSONObject("Valute");
                    Iterator<String> x = valute.keys();

                    while (x.hasNext()) {
                        String key = x.next();
                        JSONObject jsonCurrency = valute.getJSONObject(key);
                        String charCode = jsonCurrency.getString("CharCode");
                        int nominal = jsonCurrency.getInt("Nominal");
                        String name = jsonCurrency.getString("Name");
                        double value = jsonCurrency.getDouble("Value");
                        double previous = jsonCurrency.getDouble("Previous");

                        String date = response.getString("Timestamp");
                        String id = jsonCurrency.getString("ID");

                        Currency currency = new Currency(id, charCode, nominal, name, value, previous, date);
                        currencyAppDatabase.getCurrencyDAO().addCurrency(currency);
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void showNumberOfRecordsInDb() {
        int number = 0;
        ArrayList currencies = (ArrayList) currencyAppDatabase.getCurrencyDAO().getAllCurrencies();
        for (Object currency : currencies) {
            number++;
        }
        System.out.println("!!!!!!Number of records in DB!!!!!!"+number);
    }
}