package com.example.currencylist.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Data.CurrencyAppDatabase;

public class Settings extends Fragment {

    String chosenCurrencyCharCode = "USD";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Here's our DB builder
        CurrencyAppDatabase currencyAppDatabase = Room.databaseBuilder(requireContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        LineChart chart = view.findViewById(R.id.chart);

        ArrayList<Currency> currencyData = (ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyData(chosenCurrencyCharCode);
        for (Currency currency : currencyData) {
            System.out.println(currency.getCharCode());
            System.out.println(currency.getId());
            System.out.println(currency.getDate());
            System.out.println(currency.getNumDate());
        }
//        currencyAppDatabase.getCurrencyDAO().deleteCurrency(currencyAppDatabase.getCurrencyDAO().getCurrency("R01235","2022-05-12T15:00:00+03:00"));
        List<Entry> entries = new ArrayList<>();
        for (Currency currency : currencyData) {
            // turn your data into Entry objects
            entries.add(new Entry(currency.getNumDate(),(float) currency.getValue()/currency.getNominal()));
        }
        LineDataSet dataSet = new LineDataSet(entries, chosenCurrencyCharCode); // add entries to dataset
        dataSet.setColor(ContextCompat.getColor(requireContext(),R.color.downColor));
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