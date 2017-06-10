
package com.udacity.stockhawk.sync;

/**
 * Created by mac on 6/7/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.udacity.stockhawk.StockHawkApp;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.StockDataPoint;
import com.udacity.stockhawk.data.StockDataPointContainer;

import java.util.List;

import timber.log.Timber;

public class DataPointResponseListener extends QuoteResponseListener<StockDataPointContainer> {

    public DataPointResponseListener (StockDataPointContainer container) {
        super(container);
    }

    @Override
    public void onResponse (StockDataPointContainer container) {
        super.onResponse(container);

        if (this.isValid()) {
            Timber.d("--------------------");
            Timber.d("Stock Data: ");
            for (StockDataPoint data : container.getStockData()) {
                Timber.d("Id %s", data.getSymbol());
                Timber.d("Item %s", data.getItem());
                Timber.d("Value %s", data.getValue());
            }
            Timber.d("--------------------");

            // each data point in the container should correspond to a column in the database
            // there can be more columns in the database than data points retrieved from the server
            // but not the other way around
            ContentValues quoteCV = new ContentValues();
            List<StockDataPoint> allData = container.getStockData();
            quoteCV.put(Contract.Quote.COLUMN_SYMBOL, allData.get(0).getSymbol());

            for (StockDataPoint data : allData) {
                String columnName = Contract.Quote.quoteDataPoints.get(data.getItem());
                if (columnName != null) {
                    quoteCV.put(columnName, data.getValue());
                } else {
                    Timber.e("Error in stock data point handling - no corresponding column " +
                            "in database for the item %s", data.getItem());
                }
            }

            Context context = StockHawkApp.getContext();
            context.getContentResolver().insert(Contract.Quote.URI, quoteCV);
            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
            context.sendBroadcast(dataUpdatedIntent);
        }
    }
}
