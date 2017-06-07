package com.udacity.stockhawk.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 5/18/17.
 */

public class HistoricalData {

    public class HistoryPoint {

        private String date;

        private float value;

        public String getDate () { return date; }

        public float getValue () { return value; }
    }

    private List<HistoryPoint> data;

    private String identifier;

    private String item;

    @SerializedName("result_count")
    private int count;

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("total_pages")
    private int totalPages;

    //public HistoricalData () { data = new ArrayList<>(); }

    public List<HistoryPoint> getHistory () { return data; }

    public int size () { return data.size(); }

    public String getId () { return identifier; }

    public String getItem () { return item; }
}
