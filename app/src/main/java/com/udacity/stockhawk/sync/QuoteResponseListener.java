package com.udacity.stockhawk.sync;

import com.android.volley.Response;

import timber.log.Timber;

public class QuoteResponseListener<T> implements Response.Listener<T> {

    private static final String TAG = QuoteResponseListener.class.getCanonicalName();

    protected static final String ACTION_DATA_UPDATED = "com.udacity.stockhawk.ACTION_DATA_UPDATED";

    protected T result;

    public QuoteResponseListener (T result) { this.result = result; }

    @Override
    public void onResponse (T response) {
        Timber.d("Response listener reached %s.", TAG);
        if (response != null) {
            Timber.d("Response is valid.");
            result = response;
            Timber.d(response.toString());
        } else {
            //showErrorMessage(getString(R.string.null_response));
            // @TODO find another method to signal the result is shit
            result = null;
            Timber.e("Response listener result is null %s.", TAG);
        }
    }

    public boolean isValid () { return result != null; }
}
