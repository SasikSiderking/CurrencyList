package Network;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.currencylist.Currency;
import com.example.currencylist.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import Data.CurrencyAppDatabase;

public class NetworkWorker {

    String url = "https://www.cbr-xml-daily.ru/daily_json.js";
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
                Toast.makeText(context, "Данные обновлены",Toast.LENGTH_SHORT).show();
                String previousUrl;
                try {
                    JSONObject valute = response.getJSONObject("Valute");
                    Iterator<String> x = valute.keys();

                    currencyAppDatabase = Room.databaseBuilder(context, CurrencyAppDatabase.class, "currencyDB")
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB

                    String date = response.getString("Date");
                    String lastSavedDate = currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate().get(0).getDate();
                    if (!date.equals(lastSavedDate)){
                        previousUrl = "https://" + response.getString("PreviousURL");
                        url = previousUrl;
                        saveCurrencies(context);
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
                    currencyAppDatabase.close();
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
     }
}

