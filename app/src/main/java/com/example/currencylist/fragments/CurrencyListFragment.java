package com.example.currencylist.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.example.currencylist.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

import Data.CurrencyAppDatabase;

public class CurrencyListFragment extends Fragment {

    private static ArrayList<Currency> currencies;
    private RecyclerView recyclerView;
    private com.example.currencylist.RecyclerViewAdapter recyclerViewAdapter;
    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        currencies = new ArrayList<>();

        currencyAppDatabase = Room.databaseBuilder(requireContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

                if (currencyAppDatabase.getCurrencyDAO().getAllCurrencies() != null){
            for (int i = 0; i<34;i++){
                currencies.add(currencyAppDatabase.getCurrencyDAO().getAllCurrencies().get(i));
            }
        }
                recyclerViewAdapter = RecyclerViewAdapter.getInstance(requireContext(), currencies);
                recyclerView.setAdapter(recyclerViewAdapter);
    }
}