package com.example.currencylist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;

import com.example.currencylist.fragments.MyXAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import Data.CurrencyAppDatabase;

public class graphics extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Here's our DB builder
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        String chosenCurrencyCharCode = getIntent().getStringExtra("charCode");

        CurrencyAppDatabase currencyAppDatabase = Room.databaseBuilder(getApplicationContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        LineChart chart = findViewById(R.id.chart);

        ArrayList<Currency> currencyData = (ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyData(chosenCurrencyCharCode);
        for (Currency currency : currencyData) {
            System.out.println(currency.getCharCode());
            System.out.println(currency.getId());
            System.out.println(currency.getDate());
            System.out.println(currency.getNumDate());
        }
//        currencyAppDatabase.getCurrencyDAO().deleteCurrency(currencyAppDatabase.getCurrencyDAO().getCurrency("R01235","2022-05-12T20:00:00+03:00"));
        List<Entry> entries = new ArrayList<>();
        for (Currency currency : currencyData) {
            // turn your data into Entry objects
            entries.add(new Entry(currency.getNumDate(), (float) currency.getValue() / currency.getNominal()));
        }
        LineDataSet dataSet = new LineDataSet(entries, chosenCurrencyCharCode); // add entries to dataset
        dataSet.setColor(ContextCompat.getColor(getApplicationContext(), R.color.downColor));
        LineData lineData = new LineData(dataSet);

        chart.setNoDataText("No currencies?");
        chart.setBorderColor(0);
        chart.setAutoScaleMinMaxEnabled(true);


        chart.getXAxis().setValueFormatter(new MyXAxisValueFormatter());
        Description description = new Description();
        description.setText("График курса валюты в рублях");
        chart.setDescription(description);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        currencyAppDatabase.close();
    }
}