/*
 * Copyright (C) 2017 Marius Cetateanu
 *
 */
package com.udacity.stockhawk.net;

import android.net.Uri;
import android.util.Log;

import com.udacity.stockhawk.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import timber.log.Timber;



/**
 * These utilities will be used to communicate with the servers at www.intrinio.com .
 */
public final class NetworkUtils {

    public enum Frequency {

        DAILY("daily"),
        WEEKLY("weekly"),
        MONTHLY("monthly"),
        QUARTERLY("quarterly"),
        YEARLY("yearly");

        private final String tag;

        Frequency (String tag) {
            this.tag = tag;
        }

        public String getTag () {
            return this.tag;
        }

    }

    public enum StockItemType {

        CLOSE_PRICE("close_price"),
        MARKETCAP("marketcap"),
        ABSOLUTE_CHANGE("change"),
        PERCENT_CHANGE("percent_change"),
        SHARES_BASIC_OUT("weightedavebasicsharesos");

        private final String tag;

        StockItemType (String tag) {
            this.tag = tag;
        }

        public String getTag () {
            return this.tag;
        }
    }

    public static final StockItemType[] ALL_STOCK_ITEMS = new StockItemType [] {StockItemType.CLOSE_PRICE,
        StockItemType.MARKETCAP, StockItemType.ABSOLUTE_CHANGE,StockItemType.PERCENT_CHANGE, StockItemType.SHARES_BASIC_OUT};

    public interface APIContract {
        //The API KEY MUST NOT BE PUBLISHED. It is possible to generate a new one for free
        // from www.intrinio.com
        String USERNAME_API_KEY = BuildConfig.USERNAME_API_KEY;
        String PASSWD_API_KEY = BuildConfig.PASSWD_API_KEY;
    }

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String INTRINIO_BASEPATH = "https://api.intrinio.com";

    private static final String DATA_POINT_PATH = "data_point";

    private static final String COMPANIES_PATH = "companies";

    private static final String HISTORICAL_PATH = "historical_data";

    private static final String TICKER_PARAM = "ticker";

    private static final String ID_PARAM = "identifier";

    private static final String ITEM_PARAM = "item";

    private static final String START_DATE_PARAM = "start_date";

    private static final String STOP_DATE_PARAM = "stop_date";

    private static final String FREQ_PARAM = "frequency";

    private static final String TYPE_PARAM = "type";

    private static final String PAGE_PARAM = "page";

    private static final String ITEM_CLOSE_PRICE = "close_price";


    /**
     * Builds the URL used to talk to the stock data server in order to get company information.
     *
     * @param companySymbol company stock symbol
     * @return URL to use to query the stock data server.
     */
    public static URL buildCompanyInfoUrl (String companySymbol, int pageToLoad) {
        Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendPath(COMPANIES_PATH)
                .appendQueryParameter(ID_PARAM, companySymbol)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException mue) {
            Timber.e(mue);
        }

        Timber.d("Built Request URL %s", url);

        return url;
    }

    /**
     * Builds the URL used to talk to the stock data server in order to get company information.
     *
     * @param companySymbol company stock symbol
     * @return URL to use to query the stock data server.
     */
    public static URL buildHistoryUrl (String companySymbol, Calendar from, Calendar to, Frequency frequency, int
            pageToLoad) {
        Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendPath(HISTORICAL_PATH)
                .appendQueryParameter(ID_PARAM, companySymbol)
                .appendQueryParameter(ITEM_PARAM, ITEM_CLOSE_PRICE)
                .appendQueryParameter(START_DATE_PARAM, from.toString())
                .appendQueryParameter(STOP_DATE_PARAM, to.toString())
                .appendQueryParameter(FREQ_PARAM, frequency.getTag())
                .appendQueryParameter(TYPE_PARAM, "FY")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException mue) {
            Timber.e(mue);
        }

        Timber.d("Built Request URL %s", url);

        return url;
    }

    private interface Appender {
        public String getItems (StockItemType[] stockItems);
    }
    /**
     * Builds the URL used to talk to the stock data server in order to get company information.
     *
     * @param   companySymbol company stock symbol
     * @param   items type of stock items data points to retrieve
     * @return  URL to use to query the stock data server.
     */
    public static URL buildAllDataPointUrl (String companySymbol, final StockItemType[] items) {
        final Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendPath(DATA_POINT_PATH)
                .appendQueryParameter(ID_PARAM, companySymbol)
                .appendQueryParameter(ITEM_PARAM, new Appender ()
                    {
                        @Override
                        public String getItems (StockItemType[] stockItems) {
                            String result = "";
                            for (StockItemType item : stockItems) {
                                result += item.getTag();
                                result += ",";
                            }
                            return result;
                        }
                    }.getItems(items))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException mue) {
            Timber.e(mue);
        }

        Timber.d("Built Request URL %s", url);

        return url;
    }
}