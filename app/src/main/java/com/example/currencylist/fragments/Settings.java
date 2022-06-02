package com.example.currencylist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.example.currencylist.RecyclerViewAdapter;

import java.util.ArrayList;

import Data.CurrencyAppDatabase;
import Network.NetworkWorker;

public class Settings extends Fragment {

    Button button;
    CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder
    NetworkWorker networkWorker = new NetworkWorker();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currencyAppDatabase = Room.databaseBuilder(requireContext(), CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        button = view.findViewById(R.id.reload_button);
        button.setOnClickListener(view1 -> {
            networkWorker.saveCurrencies(getContext());
            Toast.makeText(requireContext(), "Данные обновлены",Toast.LENGTH_SHORT).show();
            RecyclerViewAdapter.getInstance(new ArrayList<>()).change((ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate());
        });
    }
}