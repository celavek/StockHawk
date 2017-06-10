package com.udacity.stockhawk.sync;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.udacity.stockhawk.StockHawkApp;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.HistoricalStockData;

import timber.log.Timber;

/**
 * Created by mac on 6/7/17.
 */

public class HistoryResponseListener extends QuoteResponseListener<HistoricalStockData> {

    public HistoryResponseListener (HistoricalStockData history) {
        super(history);
    }

    @Override
    public void onResponse (HistoricalStockData history) {
        Timber.d("Historical data size is: %s", String.valueOf(history.size()));
        super.onResponse(history);

        if (this.isValid()) {
            Timber.d("--------------------");
            Timber.d("Historical Data: ");
            for (HistoricalStockData.HistoryPoint point : history.getHistory()) {
                Timber.d("Date %s", point.getDate());
                Timber.d("Value %s", point.getValue());
            }
            Timber.d("--------------------");

            // @TODO this comes from the network as JSON. How to avoid converting it back and forth to
            // Object and JSON?
            String historyAsJson = new Gson().toJson(history.getHistory());

            ContentValues quoteCV = new ContentValues();
            quoteCV.put(Contract.Quote.COLUMN_SYMBOL, history.getStockSymbol());
            quoteCV.put(Contract.Quote.COLUMN_HISTORY, historyAsJson.toString());

            Context context = StockHawkApp.getContext();
            context.getContentResolver().insert(Contract.Quote.URI, quoteCV);
            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
            context.sendBroadcast(dataUpdatedIntent);
        }
    }
}
