package com.udacity.stockhawk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.stockhawk.data.Contract.Quote;


class DbHelper extends SQLiteOpenHelper {
    
    private static final String NAME = "Stawk.db";
    private static final int VERSION = 2;


    DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INFO = "info";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_ABSOLUTE_CHANGE = "absolute_change";
    public static final String COLUMN_PERCENTAGE_CHANGE = "percentage_change";
    public static final String COLUMN_MARKET_CAP = "market_cap";
    public static final String COLUMN_SHARES_OUTSTANDING = "shares_outstanding";
    public static final String COLUMN_HISTORY = "history";
    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + Quote.TABLE_NAME + " ("
                + Quote._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Quote.COLUMN_SYMBOL + " TEXT NOT NULL, "
                + Quote.COLUMN_NAME + " TEXT NOT NULL, "
                + Quote.COLUMN_INFO + " TEXT NOT NULL, "
                + Quote.COLUMN_PRICE + " REAL NOT NULL, "
                + Quote.COLUMN_ABSOLUTE_CHANGE + " REAL NOT NULL, "
                + Quote.COLUMN_PERCENTAGE_CHANGE + " REAL NOT NULL, "
                + Quote.COLUMN_MARKET_CAP + " REAL NOT NULL, "
                + Quote.COLUMN_SHARES_OUTSTANDING + " REAL NOT NULL, "
                + Quote.COLUMN_HISTORY + " TEXT NOT NULL, "
                + "UNIQUE (" + Quote.COLUMN_SYMBOL + ")";

        db.execSQL(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Quote.TABLE_NAME);

        onCreate(db);
    }
}
