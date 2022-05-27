package com.example.currencylist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.example.currencylist.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import Data.CurrencyAppDatabase;

public class Settings extends Fragment {

    Button button;
    RequestQueue requestQueue;
    CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder
    String url = "https://www.cbr-xml-daily.ru/daily_json.js";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currencyAppDatabase = Room.databaseBuilder(getContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        button = view.findViewById(R.id.reload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCurrencies(url);
            }
        });
    }

    private void getCurrencies(String url, VolleyCallback callback) {
        requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                {
                    callback.onSuccessResponse(response);
                }
            }
        }, error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    public void saveCurrencies(String url) {
//        progressBar.setVisibility(View.VISIBLE);
        getCurrencies(url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject response) {
                System.out.println("Booooooooooooooooooooobaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
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
//                progressBar.setVisibility(View.GONE);
            }
        });
    }
}