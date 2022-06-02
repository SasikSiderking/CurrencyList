package com.example.currencylist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.example.currencylist.RecyclerViewAdapter;

import java.util.ArrayList;

import Data.CurrencyAppDatabase;
import Services.UpdateService;

public class CurrencyListFragment extends Fragment {

    private com.example.currencylist.RecyclerViewAdapter recyclerViewAdapter;

   public static ArrayList<Currency> currencies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = view.findViewById(R.id.search_view);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            CurrencyAppDatabase currencyAppDatabase = Room.databaseBuilder(requireContext(), CurrencyAppDatabase.class, "currencyDB")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();

            if (currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate() != null){
                currencies.clear();
                currencies.addAll(currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate());

            }
//                for (Currency currency : currencies) {
//            System.out.println(currency.getCharCode());
//        }
                recyclerViewAdapter = new RecyclerViewAdapter((ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate());
            RecyclerViewAdapter.instance = recyclerViewAdapter;
                recyclerView.setAdapter(recyclerViewAdapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        recyclerViewAdapter.filter(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        recyclerViewAdapter.filter(newText);
                        return true;
                    }
                });
    }
}