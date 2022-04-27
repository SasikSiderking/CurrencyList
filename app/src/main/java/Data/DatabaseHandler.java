package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler( Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CARS_TABLE = "CREATE TABLE "+
                Util.TABLE_NAME+"("+
                Util.KEY_ID+" INTEGER PRIMARY KEY,"+
                Util.KEY_CHAR_CODE+" TEXT,"+
                Util.KEY_NOMINAL+" INTEGER,"+
                Util.KEY_NAME+" TEXT,"+
                Util.KEY_VALUE+" REAL,"+
                Util.KEY_PREVIOUS_VALUE+" REAL,"+
                Util.KEY_DATE+" TEXT,";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
