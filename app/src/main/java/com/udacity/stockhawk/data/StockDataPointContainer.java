package com.udacity.stockhawk.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 5/18/17.
 */

public class StockDataPointContainer {

    @SerializedName("result_count")
    private int resultCount;

    @SerializedName("api_call_credits")
    private int callCredits;

    @SerializedName("data")
    private List<StockDataPoint> stockData;

    public List<StockDataPoint> getStockData () { return stockData; }

    public int getResultCount () { return resultCount; }

    public int getCallCredits () { return callCredits; }
}
