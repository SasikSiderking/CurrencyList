package Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.currencylist.Currency;

import java.util.List;

@Dao
public interface currencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addCurrency(Currency currency);

    @Update
    public void updateCurrency(Currency currency);

    @Delete
    public void deleteCurrency(Currency currency);

    @Query("select * from currencies")
    public List<Currency> getAllCurrencies();

    @Query("select * from currencies where id ==:id and date ==:date ")
    public Currency getCurrency(String id,String date);

    @Query("select * from currencies where charCode ==:charCode")
    public List<Currency> getCurrencyData(String charCode);

    @Query("select * from currencies where date == (select max (date) from currencies)")
    public List<Currency> getCurrencyDataByDate();
}