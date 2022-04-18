package com.example.currencylist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.example.currencylist.RecyclerViewAdapter recyclerViewAdapter;
    private static ArrayList<Currency> currencies;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currencies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        String url = "https://www.cbr-xml-daily.ru/daily_json.js";
        getCurrencies(url);
    }

    private void getCurrencies(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject valute = response.getJSONObject("Valute");
                Iterator<String> x = valute.keys();

                while (x.hasNext()) {
                    String key = (String) x.next();
                    JSONObject jsonCurrency = valute.getJSONObject(key);
                    String charCode = jsonCurrency.getString("CharCode");
                    String nominal = jsonCurrency.getString("Nominal");
                    String name = jsonCurrency.getString("Name");
                    String value = jsonCurrency.getString("Value");
                    String previous = jsonCurrency.getString("Previous");

                    Currency currency = new Currency(charCode, nominal, name, value, previous);
                    currencies.add(currency);
                }
                recyclerViewAdapter = RecyclerViewAdapter.getInstance(MainActivity.this, currencies);
                recyclerView.setAdapter(recyclerViewAdapter);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }
}