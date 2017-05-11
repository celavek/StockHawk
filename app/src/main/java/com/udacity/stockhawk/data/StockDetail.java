package com.udacity.stockhawk.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StockDetail implements Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAbsoluteChange () {
        return absoluteChange;
    }

    public void setAbsoluteChange (float change) {
        this.absoluteChange = change;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public float getSharesFloat() {
        return sharesFloat;
    }

    public void setSharesFloat(float sharesFloat) {
        this.sharesFloat = sharesFloat;
    }

    public float getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(float sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public float getSharesOwned() {
        return sharesOwned;
    }

    public void setSharesOwned(float sharesOwned) {
        this.sharesOwned = sharesOwned;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    private String name;

    private float price;

    private float absoluteChange;

    private double marketCap;

    private float sharesFloat;

    private float sharesOutstanding;

    private float sharesOwned;

    private String history;


    @Override
    public int describeContents() {
        return 0;
    }

    public StockDetail () { }

    public StockDetail (Parcel in) {
        this.name = in.readString();
        this.price = in.readFloat();
        this.absoluteChange = in.readFloat();
        this.marketCap = in.readFloat();
        this.sharesFloat = in.readFloat();
        this.sharesOutstanding = in.readFloat();
        this.sharesOwned = in.readFloat();
        this.history = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.price);
        dest.writeFloat(this.absoluteChange);
        dest.writeFloat(this.sharesFloat);
        dest.writeFloat(this.sharesOutstanding);
        dest.writeFloat(this.sharesOwned);
        dest.writeString(this.history);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final transient Parcelable.Creator<StockDetail> CREATOR = new Parcelable.Creator<StockDetail>() {

        public StockDetail createFromParcel(Parcel in) {
            return new StockDetail(in);
        }

        public StockDetail[] newArray(int size) {
            return new StockDetail[size];
        }
    };

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof StockDetail) {
            StockDetail toCompare = (StockDetail) obj;
            return (this.name.equalsIgnoreCase(toCompare.getName()));
        }

        return false;
    }

    @Override
    public int hashCode () {
        return (this.getName()).hashCode();
    }
}
