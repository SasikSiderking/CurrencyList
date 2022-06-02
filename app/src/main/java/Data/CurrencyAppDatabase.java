package Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.currencylist.Currency;

@Database(entities = {Currency.class}, version = 5)
public abstract class CurrencyAppDatabase extends RoomDatabase {

    public abstract currencyDAO getCurrencyDAO();

}
