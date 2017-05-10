package com.udacity.stockhawk.data;

/**
 * Created by mac on 5/10/17.
 */

public class StockDetailBuilder {

    private StockDetail stockDetail;

    public StockDetailBuilder () {
        stockDetail = new StockDetail();
    }

    public StockDetailBuilder name (String name) {
        stockDetail.setName(name);
        return this;
    }

    public StockDetailBuilder price (float price) {
        stockDetail.setPrice(price);
        return this;
    }


    public StockDetailBuilder absoluteChange (float change) {
        stockDetail.setAbsoluteChange(change);
        return this;
    }

    public StockDetailBuilder marketCap (double cap) {
        stockDetail.setMarketCap(cap);
        return this;
    }

    public StockDetailBuilder sharesFloat (float shares) {
        stockDetail.setSharesFloat(shares);
        return this;
    }

    public StockDetailBuilder sharesOutstanding (float shares) {
        stockDetail.setSharesOutstanding(shares);
        return this;
    }

    public StockDetailBuilder sharesOwned (float shares) {
        stockDetail.setSharesOwned(shares);
        return this;
    }

    public StockDetail build () {
        return stockDetail;
    }
}
