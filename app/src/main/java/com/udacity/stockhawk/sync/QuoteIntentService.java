package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Pair;
import android.widget.Toast;

import com.udacity.stockhawk.StockHawkApp;

import timber.log.Timber;


public class QuoteIntentService extends IntentService {

    private Handler mHandler;

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate () {
        super.onCreate();
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        Pair<Boolean, String> result = QuoteSyncJob.getQuotes(getApplicationContext());

        if (result.first) {
            final class ToastMaker implements Runnable {
                String mMessage;

                ToastMaker (String message) {
                    mMessage = message;
                }

                @Override
                public void run () {
                    Toast.makeText(StockHawkApp.getContext(), mMessage, Toast.LENGTH_LONG).show();
                }

            }
            mHandler.post(new ToastMaker(result.second));
        }
    }
}
