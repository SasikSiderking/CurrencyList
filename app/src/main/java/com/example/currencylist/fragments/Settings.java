package com.example.currencylist.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import Data.CurrencyAppDatabase;

public class Settings extends Fragment {

    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        LineChart chart = (LineChart) view.findViewById(R.id.chart);
//
//        ArrayList<Currency> currencyData = (ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyData("USD");
//        List<Entry> entries = new ArrayList<Entry>();
//        for (Currency currency : currencyData) {
//            // turn your data into Entry objects
//            entries.add(new Entry((float) (currency.getValue()/currency.getNominal()), currency.getDate()));
//        }
    }
}