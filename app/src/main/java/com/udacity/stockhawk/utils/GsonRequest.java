/*
 * Adapted from the Volley documentation at
 * https://developer.android.com/training/volley/request-custom.html
 */
package com.udacity.stockhawk.utils;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private Map<String, String> headers;
    private final Response.Listener<T> responseListener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    public GsonRequest (String url, Class<T> clazz, Response.Listener<T> responseListener, Response.ErrorListener errorListener) {
        this(url, clazz, null, responseListener, errorListener);
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest (String url, Class<T> clazz, Map<String, String> headers, Response.Listener<T> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.responseListener = responseListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null) {
            headers = new HashMap<String, String>();
            headers.put(
                    "Authorization",
                    String.format("Basic %s", Base64.encodeToString(
                            String.format("%s:%s", NetworkUtils.APIContract.USERNAME_API_KEY, NetworkUtils
                                    .APIContract.PASSWD_API_KEY).getBytes(),
                            Base64.DEFAULT)));
        }

        return headers;
    }

    @Override
    protected void deliverResponse (T response) {
        responseListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse (NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}