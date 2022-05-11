package com.example.currencylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import Data.CurrencyAppDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.example.currencylist.RecyclerViewAdapter recyclerViewAdapter;
    private static ArrayList<Currency> currencies;
    private RequestQueue requestQueue;

    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currencies = new ArrayList<>();

        String url = "https://www.cbr-xml-daily.ru/daily_json.js";

        View overlay = findViewById(R.id.mainPage);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        currencyAppDatabase = Room.databaseBuilder(getApplicationContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        saveCurrencies(url);
        putCurrenciesInRecyclerView();
    }

    private void getCurrencies(String url, VolleyCallback callback) {
        requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                {
                    callback.onSuccessResponse(response);
                    System.out.println("!!!!!Request!!!!");
                }
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    public void saveCurrencies(String url) {
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

                        String date = response.getString("Date");
                        String id = jsonCurrency.getString("ID");

                        Currency currency = new Currency(id, charCode, nominal, name, value, previous, date);
                        currencyAppDatabase.getCurrencyDAO().addCurrency(currency);
                        currencies.add(currency);
                    }
                    recyclerViewAdapter = RecyclerViewAdapter.getInstance(MainActivity.this, currencies);
                    recyclerView.setAdapter(recyclerViewAdapter);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
    }

    public void putCurrenciesInRecyclerView() {
        int number = 0;
        ArrayList currencies = (ArrayList) currencyAppDatabase.getCurrencyDAO().getAllCurrencies();
        for (Object currency : currencies) {
            number++;
        }
        System.out.println("!!!!!!Number of records in DB!!!!!!"+number);
    }
}