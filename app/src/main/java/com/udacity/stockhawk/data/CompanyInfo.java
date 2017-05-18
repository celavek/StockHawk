package com.udacity.stockhawk.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 5/18/17.
 */

public class CompanyInfo {

    @SerializedName("ticker")
    private String symbol;

    private String name;

    @SerializedName("stock_exchange")
    private String exchange;

    @SerializedName("short_description")
    private String description;

    private String ceo;

    @SerializedName("company_url")
    private String url;

    @SerializedName("business_address")
    private String address;

    @SerializedName("hq_country")
    private String headquarters;

    @SerializedName("industry_group")
    private String industry;

    public String getSymbol () { return symbol; }

    public String getName () { return name; }

    public String getExchange () { return exchange; }

    public String getDescription () { return description; }

    public String getCeo () { return ceo; }

    public String getUrl () { return url; }

    public String getAddress () { return address; }

    public String getHeadquartersLocation () { return headquarters; }

    public String getIndustry () { return industry; }
}
