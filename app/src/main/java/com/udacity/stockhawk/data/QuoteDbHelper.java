package com.udacity.stockhawk.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.stockhawk.data.Contract.Quote;

import timber.log.Timber;


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
                + Quote.COLUMN_NAME + " TEXT, "
                + Quote.COLUMN_INFO + " TEXT, "
                + Quote.COLUMN_PRICE + " REAL, "
                + Quote.COLUMN_ABSOLUTE_CHANGE + " REAL, "
                + Quote.COLUMN_PERCENTAGE_CHANGE + " REAL, "
                + Quote.COLUMN_MARKETCAP + " REAL, "
                + Quote.COLUMN_SHARES_OUTSTANDING + " REAL, "
                + Quote.COLUMN_HISTORY + " TEXT, "
                + "UNIQUE (" + Quote.COLUMN_SYMBOL + "))" ;

        try {
            db.execSQL(builder);
        } catch (SQLException sqe) {
            Timber.e("Failed to create database. Something went wrong - %s", sqe.getLocalizedMessage());
        }
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Quote.TABLE_NAME);

        onCreate(db);
    }
}
