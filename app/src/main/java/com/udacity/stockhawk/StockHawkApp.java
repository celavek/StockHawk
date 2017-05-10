package com.udacity.stockhawk;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class StockHawkApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Context getContext () {
        return mContext;
    }
}
