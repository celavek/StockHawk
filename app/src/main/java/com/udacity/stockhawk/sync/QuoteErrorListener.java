package com.udacity.stockhawk.sync;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.StockHawkApp;

import timber.log.Timber;

public class QuoteErrorListener implements Response.ErrorListener {

    private static final String TAG = QuoteErrorListener.class.getCanonicalName();

    @Override
    public void onErrorResponse (VolleyError error) {

        Context ctx = StockHawkApp.getContext();
        String message = "";

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            Timber.e("Network request error %d", response.statusCode);
            switch (response.statusCode) {
                case 401:
                    message = ctx.getString(R.string.intrinio_unauthorized_error);;
                    break;
                case 403:
                    message = ctx.getString(R.string.intrinio_forbidden_error);;
                    break;
                case 404:
                    message = ctx.getString(R.string.intrinio_notfound_error);;
                    break;
                case 429:
                    message = ctx.getString(R.string.intrinio_toomany_error);;
                    break;
                case 500:
                    message = ctx.getString(R.string.intrinio_internal_error);;
                    break;
                case 503:
                    message = ctx.getString(R.string.intrinio_unavailable_error);;
                    break;
                default:
                    message = ctx.getString(R.string.intrinio_unknown_error);;
                    break;
            }
        }

        if (error instanceof NetworkError) {
            message += ctx.getString(R.string.network_error);
        } else if (error instanceof ServerError) {
            message += ctx.getString(R.string.server_error);
        } else if (error instanceof AuthFailureError) {
            message += ctx.getString(R.string.authentication_failure_error);
        } else if (error instanceof ParseError) {
            message += ctx.getString(R.string.parse_error);
        } else if (error instanceof TimeoutError) {
            message += ctx.getString(R.string.timeout_error);
        }
        message += String.valueOf(error);
        Timber.d(message);

        //showErrorMessage(message);
    }
}
