package com.udacity.stockhawk.sync;

import com.android.volley.Response;

public class QuoteResponseListener<T> implements Response.Listener<T> {

    private T result;

    public QuoteResponseListener (T result) { this.result = result; }

    @Override
    public void onResponse (T response) {
        if (response != null) {
            result = response;
        } else {
            //showErrorMessage(getString(R.string.null_response));
            // @TODO find another method ro signal the result is shit
            result = null;
        }
    }
}
