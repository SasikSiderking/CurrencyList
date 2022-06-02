package Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.currencylist.Currency;
import com.example.currencylist.RecyclerViewAdapter;
import com.example.currencylist.VolleyCallback;
import com.example.currencylist.fragments.CurrencyListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import Data.CurrencyAppDatabase;

public class NetworkWorker {
    String url = "https://www.cbr-xml-daily.ru/daily_json.js";
    String lastSavedDate="";
    CurrencyAppDatabase currencyAppDatabase;

    private void getCurrencies(String url, Context context, VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                {
                    callback.onSuccessResponse(response);
                }
            }
        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    public void saveCurrencies(Context context) {
        getCurrencies(url, context, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject response) {
                String previousUrl;
                try {
                    JSONObject valute = response.getJSONObject("Valute");
                    Iterator<String> x = valute.keys();

                    currencyAppDatabase = Room.databaseBuilder(context, CurrencyAppDatabase.class, "currencyDB")
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

                    String date = response.getString("Date");
                    if(!currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate().isEmpty() && lastSavedDate.equals("")){
                        Log.e("Message","Second");
                        lastSavedDate = currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate().get(0).getDate();
                    }
                    else {
                        lastSavedDate = "2022-05-28T11:30:00+03:00";
                    }
                    if (!date.equalsIgnoreCase(lastSavedDate)){
                        previousUrl = "https:" + response.getString("PreviousURL").replace("\\\\","/");
                        url = previousUrl;
                            saveCurrencies(context);
                    }
                    else{
                        RecyclerViewAdapter.getInstance(CurrencyListFragment.currencies).change((ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate());
                        Log.e("Message", "Second");
                        currencyAppDatabase.close();
                        return;
                    }
                    while (x.hasNext()) {
                        String key = x.next();
                        JSONObject jsonCurrency = valute.getJSONObject(key);

                        String charCode = jsonCurrency.getString("CharCode");
                        int nominal = jsonCurrency.getInt("Nominal");
                        String name = jsonCurrency.getString("Name");
                        double value = jsonCurrency.getDouble("Value");
                        double previous = jsonCurrency.getDouble("Previous");
                        String id = jsonCurrency.getString("ID");

                        Currency currency = new Currency(id, charCode, nominal, name, value, previous, date);
                        currencyAppDatabase.getCurrencyDAO().addCurrency(currency);
                    }
                    Log.e("Message","First");
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
     }
}

