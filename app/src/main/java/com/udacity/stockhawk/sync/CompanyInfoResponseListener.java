package com.udacity.stockhawk.sync;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.udacity.stockhawk.StockHawkApp;
import com.udacity.stockhawk.data.CompanyInfo;
import com.udacity.stockhawk.data.Contract;

import timber.log.Timber;

/**
 * Created by mac on 6/7/17.
 */

public class CompanyInfoResponseListener extends QuoteResponseListener<CompanyInfo> {

    public CompanyInfoResponseListener (CompanyInfo info) {
        super(info);
    }

    @Override
    public void onResponse (CompanyInfo companyInfo) {
        super.onResponse(companyInfo);

        if (this.isValid()) {

            Timber.d("--------------------");
            Timber.d("Company Info: ");
            Timber.d("Name %s Symbol %s", companyInfo.getName(), companyInfo.getSymbol());
            Timber.d("Address %s", companyInfo.getAddress());
            Timber.d("CEO %s", companyInfo.getCeo());
            Timber.d("Description %s", companyInfo.getDescription());
            Timber.d("Industry %s", companyInfo.getIndustry());
            Timber.d("--------------------");

            StringBuilder info = new StringBuilder();
            info.append(companyInfo.getName()).append(",").append(companyInfo.getAddress())
                    .append(",").append(companyInfo.getCeo()).append(",")
                    .append(companyInfo.getDescription()).append(",").append(companyInfo.getIndustry());
            ContentValues quoteCV = new ContentValues();
            quoteCV.put(Contract.Quote.COLUMN_SYMBOL, companyInfo.getSymbol());
            quoteCV.put(Contract.Quote.COLUMN_NAME, companyInfo.getName());
            quoteCV.put(Contract.Quote.COLUMN_INFO, info.toString());

            Context context = StockHawkApp.getContext();

            context.getContentResolver().insert(Contract.Quote.URI, quoteCV);

            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
            context.sendBroadcast(dataUpdatedIntent);
        }
    }
}
