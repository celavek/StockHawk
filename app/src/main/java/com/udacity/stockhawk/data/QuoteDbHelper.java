package com.udacity.stockhawk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.stockhawk.data.Contract.Quote;


class QuoteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stawk.db";

    // ! during development this is more practical as it will keep the
    // database in memory allowing for proper testing
    private static final String IN_MEMORY_DATABASE = null;

    private static final int VERSION = 1;

    QuoteDbHelper (Context context) {
        super(context, /*DATABASE_NAME*/IN_MEMORY_DATABASE, null, VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
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
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Quote.TABLE_NAME);

        onCreate(db);
    }
}
