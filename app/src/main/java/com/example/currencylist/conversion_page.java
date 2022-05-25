package com.example.currencylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import Data.CurrencyAppDatabase;

public class conversion_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_page);

        Button btn1, btn2;
        TextInputLayout til1, til2;

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        til1 = findViewById(R.id.textInputLayout);
        til2 = findViewById(R.id.textInputLayout3);

        btn1.setOnClickListener(
                view -> {
                    double content = Double.parseDouble(til1.getEditText().getText().toString());
                    double ratio = 65.79;
                    double result = content/ratio;
                    String loh = String.valueOf(result);
                    til2.getEditText().setText(loh);
                }
        );
        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double content = Double.parseDouble(til2.getEditText().getText().toString());
                        double ratio = 65.79;
                        double result = content*ratio;
                        String loh = String.valueOf(result);
                        til1.getEditText().setText(loh);
                    }
                }
        );
    }

    private static ArrayList<Currency> currencies;
    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    public ArrayList<Currency> getCurrenciesFromDB (Context context){
        ArrayList<Currency> currencies;
        currencies = new ArrayList<>();

        currencyAppDatabase = Room.databaseBuilder(context, CurrencyAppDatabase.class, "currencyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

        if (currencyAppDatabase.getCurrencyDAO().getAllCurrencies() != null){
            for (int i = 0; i<34;i++){
                currencies.add(currencyAppDatabase.getCurrencyDAO().getAllCurrencies().get(i));
            }
        }
        return currencies;
    }
}