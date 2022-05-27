package com.example.currencylist.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencylist.Currency;
import com.example.currencylist.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import Data.CurrencyAppDatabase;

public class calculator extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_conversion_page);

        currencies = getCurrenciesFromDB(getContext());
        int[] pos = new int[1];

         String[] names = new String[currencies.size()];
         for(int i = 0; i < currencies.size(); i++) {
            names[i] = currencies.get(i).getName();
         }

         Spinner spinner = view.findViewById(R.id.spinner);
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, names);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);
         spinner.setSelection(10);

        TextView txt;
        txt = view.findViewById(R.id.textView2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                pos[0] = position;
                txt.setText(currencies.get(position).getCharCode());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

//        Spinner spinner2 = view.findViewById(R.id.spinner2);
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, names);
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(adapter2);
//        spinner2.setSelection(2);

        Button btn1, btn2;
        TextInputLayout til1, til2;


        btn1 = view.findViewById(R.id.button1);
        btn2 = view.findViewById(R.id.button2);
        til1 = view.findViewById(R.id.textInputLayout);
        til2 = view.findViewById(R.id.textInputLayout3);


        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double content;
                        if (til1.getEditText().getText().toString().isEmpty()) {
                            til1.getEditText().setText("1");
                            content = 1;
                        }
                        else {
                            content = Double.parseDouble(til1.getEditText().getText().toString());
                        }
                        double ratio = currencies.get(pos[0]).getValue()/currencies.get(pos[0]).getNominal();
                        double result = content*ratio;
                        String loh = String.valueOf(result);
                        til2.getEditText().setText(loh);
                    }
                }
        );
        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double content;
                        if (til2.getEditText().getText().toString().isEmpty()) {
                            til2.getEditText().setText("1");
                            content = 1;
                        }
                        else {
                            content = Double.parseDouble(til2.getEditText().getText().toString());
                        }
                        double ratio = currencies.get(pos[0]).getValue()/currencies.get(pos[0]).getNominal();
                        double result = content/ratio;
                        String loh = String.valueOf(result);
                        til1.getEditText().setText(loh);
                    }
                }
        );
    }

    private ArrayList<Currency> currencies;
    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder
    public ArrayList<Currency> getCurrenciesFromDB (Context context){
        ArrayList<Currency> currencies;
        currencies = new ArrayList<>();

        currencyAppDatabase = Room.databaseBuilder(context, CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        if (currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate() != null){
            for (int i = 0; i<34;i++){
                currencies.add(currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate().get(i));
            }
        }
        return currencies;
    }
}
