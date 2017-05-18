package com.udacity.stockhawk.data;


import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableList;

public final class Contract {

    static final String AUTHORITY = "com.udacity.stockhawk";
    static final String PATH_QUOTE = "quote";
    static final String PATH_QUOTE_WITH_SYMBOL = "quote/*";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    @SuppressWarnings("unused")
    public static final class Quote implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_QUOTE).build();
        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_INFO = "info";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_ABSOLUTE_CHANGE = "absolute_change";
        public static final String COLUMN_PERCENTAGE_CHANGE = "percentage_change";
        public static final String COLUMN_MARKET_CAP = "market_cap";
        public static final String COLUMN_SHARES_OUTSTANDING = "shares_outstanding";
        public static final String COLUMN_HISTORY = "history";
        public static final int POSITION_ID = 0;
        public static final int POSITION_SYMBOL = 1;
        public static final int POSITION_NAME = 2;
        public static final int POSITION_INFO = 3;
        public static final int POSITION_PRICE = 4;
        public static final int POSITION_ABSOLUTE_CHANGE = 5;
        public static final int POSITION_PERCENTAGE_CHANGE = 6;
        public static final int POSITION_MARKET_CAP = 7;
        public static final int POSITION_SHARES_OUTSTANDING = 8;
        public static final int POSITION_HISTORY = 9;

        public static final ImmutableList<String> QUOTE_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_SYMBOL,
                COLUMN_NAME,
                COLUMN_INFO,
                COLUMN_PRICE,
                COLUMN_ABSOLUTE_CHANGE,
                COLUMN_PERCENTAGE_CHANGE,
                COLUMN_MARKET_CAP,
                COLUMN_SHARES_OUTSTANDING,
                COLUMN_HISTORY
        );
        static final String TABLE_NAME = "quotes";

        public static Uri makeUriForStock(String symbol) {
            return URI.buildUpon().appendPath(symbol).build();
        }

        static String getStockFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }
    }
}
