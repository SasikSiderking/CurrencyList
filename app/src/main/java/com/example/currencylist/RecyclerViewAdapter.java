package com.example.currencylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CurrencyViewHolder> {
    private Context context;
    private ArrayList<Currency> currencies;

    public RecyclerViewAdapter(Context context, ArrayList<Currency> currencies){
        this.context = context;
        this.currencies = currencies;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = currencies.get(position);

        String charCode = currency.getCharCode();
        String nominal = currency.getNominal();
        String name = currency.getName();
        String value = currency.getValue();
        String previous = currency.getPrevious();

        holder.charCodeView.setText(charCode);
        holder.nominalView.setText(nominal);
        holder.nameView.setText(name);
        holder.valueView.setText(value);
        holder.previousView.setText(previous);
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
