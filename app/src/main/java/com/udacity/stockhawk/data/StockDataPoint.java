package com.udacity.stockhawk.data;

/**
 * Created by mac on 5/18/17.
 */

public class StockDataPoint {

    private String identifier;

    private String item;

    private String value;

    StockDataPoint (String identifier, String item, String value)
    {
        this.identifier = identifier;
        this.item = item;
        this.value = value;
    }

    public String getSymbol () { return identifier; }

    public String getItem () { return item; }

    public String getValue () {return value; }
}
