
package com.udacity.stockhawk.sync;

/**
 * Created by mac on 6/7/17.
 */

import com.udacity.stockhawk.data.StockDataPoint;
import com.udacity.stockhawk.data.StockDataPointContainer;

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
                Timber.d("Id %s", data.getIdentifier());
                Timber.d("Item %s", data.getItem());
                Timber.d("Value %s", data.getValue());
            }
            Timber.d("--------------------");
        }
    }
}
