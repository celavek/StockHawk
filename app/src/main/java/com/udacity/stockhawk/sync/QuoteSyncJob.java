package com.udacity.stockhawk.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Pair;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.StockHawkApp;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockStats;

public final class QuoteSyncJob {

    private static final int ONE_OFF_ID = 2;
    private static final String ACTION_DATA_UPDATED = "com.udacity.stockhawk.ACTION_DATA_UPDATED";
    private static final int PERIOD = 300000;
    private static final int INITIAL_BACKOFF = 10000;
    private static final int PERIODIC_ID = 1;
    private static final int YEARS_OF_HISTORY = 2;

    private QuoteSyncJob() {
    }

    static Pair<Boolean, String> getQuotes(Context context) {

        Timber.d("Running sync job");

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -YEARS_OF_HISTORY);

        boolean invalidDetected = false;
        try {

            Set<String> stockPref = PrefUtils.getStocks(context);
            Set<String> stockCopy = new HashSet<>();
            stockCopy.addAll(stockPref);
            String[] stockArray = stockPref.toArray(new String[stockPref.size()]);

            Timber.d(stockCopy.toString());

            if (stockArray.length == 0) {
                return Pair.create(false, StockHawkApp.getContext().getResources().getString(R.string.error_no_stocks));
            }

            Map<String, Stock> quotes = YahooFinance.get(stockArray);
            Iterator<String> iterator = stockCopy.iterator();

            Timber.d(quotes.toString());

            ArrayList<ContentValues> quoteCVs = new ArrayList<>();

            while (iterator.hasNext()) {
                String symbol = iterator.next();

                Stock stock = quotes.get(symbol);
                if (stock.isValid()) {
                    StockQuote quote = stock.getQuote();

                    float price = quote.getPrice().floatValue();
                    float change = quote.getChange().floatValue();
                    float percentChange = quote.getChangeInPercent().floatValue();

                    double marketCap = 0;
                    float sharesFloat = 0.f;
                    float sharesOutstanding = 0.f;
                    float sharesOwned = 0.f;

                    StockStats stats = stock.getStats();
                    if (stats != null) {
                        marketCap = stats.getMarketCap().divide(BigDecimal.valueOf(1000000.0)).doubleValue();
                        sharesFloat = stats.getSharesFloat() / 1000000.f;
                        sharesOutstanding = stats.getSharesOutstanding() / 1000000.f;
                        //sharesOwned = stats.getSharesOwned() / 1000000.f;
                    }

                    // WARNING! Don't request historical data for a stock that doesn't exist!
                    // The request will hang forever X_x
                    List<HistoricalQuote> history = stock.getHistory(from, to, Interval.WEEKLY);

                    StringBuilder historyBuilder = new StringBuilder();

                    for (HistoricalQuote it : history) {
                        historyBuilder.append(it.getDate().getTimeInMillis());
                        historyBuilder.append(", ");
                        historyBuilder.append(it.getClose());
                        historyBuilder.append("\n");
                    }

                    ContentValues quoteCV = new ContentValues();
                    quoteCV.put(Contract.Quote.COLUMN_SYMBOL, symbol);
                    quoteCV.put(Contract.Quote.COLUMN_PRICE, price);
                    quoteCV.put(Contract.Quote.COLUMN_PERCENTAGE_CHANGE, percentChange);
                    quoteCV.put(Contract.Quote.COLUMN_ABSOLUTE_CHANGE, change);
                    quoteCV.put(Contract.Quote.COLUMN_MARKET_CAP, marketCap);
                    quoteCV.put(Contract.Quote.COLUMN_SHARES_FLOAT, sharesFloat);
                    quoteCV.put(Contract.Quote.COLUMN_SHARES_OUTSTANDING, sharesOutstanding);
                    quoteCV.put(Contract.Quote.COLUMN_SHARES_OWNED, sharesOwned);


                    quoteCV.put(Contract.Quote.COLUMN_HISTORY, historyBuilder.toString());
                    Timber.d(String.format("History: %s", historyBuilder.toString()));

                    quoteCVs.add(quoteCV);
                }
                else
                {
                    invalidDetected = true;
                    Timber.e("Error fetching some stock quotes - invalid stock symbol.");
                }
            }

            context.getContentResolver()
                    .bulkInsert(Contract.Quote.URI,
                            quoteCVs.toArray(new ContentValues[quoteCVs.size()]));

            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
            context.sendBroadcast(dataUpdatedIntent);

        } catch (IOException exception) {
            Timber.e(exception, "Error fetching stock quotes");
        }

        if (invalidDetected) {
            return Pair.create(true,
                    StockHawkApp.getContext().getResources().getString(R.string.error_invalid_stock));
        } else {
            return Pair.create(false, "");
        }
    }

    private static void schedulePeriodic(Context context) {
        Timber.d("Scheduling a periodic task");


        JobInfo.Builder builder = new JobInfo.Builder(PERIODIC_ID, new ComponentName(context, QuoteJobService.class));


        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(PERIOD)
                .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        scheduler.schedule(builder.build());
    }


    public static synchronized void initialize(final Context context) {

        schedulePeriodic(context);
        syncImmediately(context);

    }

    public static synchronized void syncImmediately(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            Intent nowIntent = new Intent(context, QuoteIntentService.class);
            context.startService(nowIntent);
        } else {

            JobInfo.Builder builder = new JobInfo.Builder(ONE_OFF_ID, new ComponentName(context, QuoteJobService.class));


            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            scheduler.schedule(builder.build());
        }
    }


}
