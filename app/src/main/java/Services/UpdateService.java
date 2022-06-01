package Services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.currencylist.Currency;
import com.example.currencylist.RecyclerViewAdapter;

import java.util.ArrayList;

import Data.CurrencyAppDatabase;
import Network.NetworkWorker;

public class UpdateService extends Service {

    private UpdateService instance;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            CurrencyAppDatabase currencyAppDatabase = Room.databaseBuilder(getApplicationContext(), CurrencyAppDatabase.class, "currencyDB")
                                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();//Building DB
                            NetworkWorker networkWorker = new NetworkWorker();
                            networkWorker.saveCurrencies(getApplicationContext());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    RecyclerViewAdapter.getInstance(new ArrayList<>()).change((ArrayList<Currency>) currencyAppDatabase.getCurrencyDAO().getCurrencyDataByDate());
                                    currencyAppDatabase.close();
                                }
                            });
                            Log.e("Tag","update executed");
                            try {
                                Thread.sleep(3600000);
//                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
