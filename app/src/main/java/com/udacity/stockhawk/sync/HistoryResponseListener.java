package com.udacity.stockhawk.sync;

import com.udacity.stockhawk.data.HistoricalData;

import timber.log.Timber;

/**
 * Created by mac on 6/7/17.
 */

public class HistoryResponseListener extends QuoteResponseListener<HistoricalData> {

    public HistoryResponseListener (HistoricalData history) {
        super(history);
    }

    @Override
    public void onResponse (HistoricalData history) {
        Timber.d("Historical data size is: %s", String.valueOf(history.size()));
        super.onResponse(history);

        if (this.isValid()) {
            Timber.d("--------------------");
            Timber.d("Historical Data: ");
            for (HistoricalData.HistoryPoint point : history.getHistory()) {
                Timber.d("Date %s", point.getDate());
                Timber.d("Value %s", point.getValue());
            }
            Timber.d("--------------------");
        }
    }
}
