package com.example.currencylist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CurrencyViewHolder> {
    private Context context;
    private ArrayList<Currency> currencies;

    public void filter(String text) {
        ArrayList<Currency> currenciesCopy = new ArrayList<>();
        currenciesCopy.addAll(currencies);
        currencies.clear();
        if(text.isEmpty()){
            currencies.addAll(currenciesCopy);
        } else{
            text = text.toLowerCase();
            for(Currency item: currenciesCopy){
                if(item.getName().toLowerCase().contains(text) || item.getCharCode().toLowerCase().contains(text)){
                    currencies.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    private static RecyclerViewAdapter instance;

    private RecyclerViewAdapter(Context context, ArrayList<Currency> currencies){
        this.context = context;
        this.currencies = currencies;
    }

    public static RecyclerViewAdapter getInstance(Context context, ArrayList<Currency> currencies){
        if (instance == null){
            instance = new RecyclerViewAdapter(context, currencies);
        }
        return instance;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false);
        return new CurrencyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = currencies.get(position);

        String charCode = currency.getCharCode();
        int nominal = currency.getNominal();
        String name = currency.getName();
        double value = currency.getValue();
        double previous = currency.getPreviousValue();

        holder.charCodeView.setText(" " + charCode + " ");
        holder.nominalView.setText(Integer.toString(nominal) + " ");
        holder.nameView.setText(name + " = ");
        holder.valueView.setText(Double.toString( value) + " ₽ ");
        holder.previousView.setText(String.format("%.2f", value - previous));

        if (value - previous < 0) {
            holder.previousView.setTextColor(Color.RED);
        }
        else {
            holder.previousView.setTextColor(Color.GREEN);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, conversion_page.class);
                intent.putExtra("charCode", charCode);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder{

        TextView charCodeView;
        TextView nominalView;
        TextView nameView;
        TextView valueView;
        TextView previousView;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            charCodeView = itemView.findViewById(R.id.charCodeView);
            nominalView = itemView.findViewById(R.id.nominalView);
            nameView = itemView.findViewById(R.id.nameView);
            valueView = itemView.findViewById(R.id.valueView);
            previousView = itemView.findViewById(R.id.previousView);
        }
    }
}
