package com.udacity.stockhawk.sync;

import com.udacity.stockhawk.data.CompanyInfo;

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
        }
    }
}
