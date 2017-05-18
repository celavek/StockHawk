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
}
