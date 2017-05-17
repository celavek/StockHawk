/*
 * Copyright (C) 2017 Marius Cetateanu
 *
 */
package com.udacity.stockhawk.utils;

import android.net.Uri;
import android.util.Log;

import com.udacity.stockhawk.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * These utilities will be used to communicate with the servers at www.intrinio.com .
 */
public final class NetworkUtils {

    public interface APIContract {
        //The API KEY MUST NOT BE PUBLISHED. It is possible to generate a new one for free
        // from www.intrinio.com
        String USERNAME_API_KEY = BuildConfig.USERNAME_API_KEY;
        String PASSWD_API_KEY = BuildConfig.PASSWD_API_KEY;
    }

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String INTRINIO_BASEPATH = "https://www.api.intrinio.com";

    public static final String POINT_DATA_TYPE = "data_point";

    public static final String TICKER = "ticker=";

    public static final String ITEM = "item=";

    public static final String UPCOMING_ENDPOINT = "movie/upcoming";

    private static final String KEY_PARAM = "api_key";

    private static final String LANGUAGE_PARAM = "language";

    private static final String PAGE_PARAM = "page";

    /**
     * Builds the URL used to talk to the movie server using a location. This location is based
     * on the query capabilities of the movie provider that we are using.
     *
     * @param queryEndpoint The location that will be queried for.
     * @return The URL to use to query the movie server.
     */
    public static URL buildRequestUrl (String queryEndpoint, int pageToLoad) {
        Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendEncodedPath(queryEndpoint)

                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(PAGE_PARAM, String.valueOf(pageToLoad))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built Request URL " + url);

        return url;
    }


    /**
     * Builds the URL used to talk to the movie server using a location. This location is based
     * on the query capabilities of the movie provider that we are using.
     *
     * @param imagePath The location that will be queried for.
     * @return The URL to use to query the movie server.
     */
    public static URL buildImageUrl (String imagePath) {
        Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendEncodedPath(imagePath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built Image URL " + url);

        return url;
    }

    /**
     * Builds the URL used to talk to the movie server using a location. This location is based
     * on the query capabilities of the movie provider that we are using.
     *
     * @param movieId movie id to get the trailers for.
     * @return The URL to use to query the movie server.
     */
    public static URL buildTrailerUrl (String movieId) {
        String trailerPath = "movie/" + movieId + "/videos";//String.format(TRAILER_ENDPOINT, movieId);
        Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendEncodedPath(trailerPath)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built trailers URL: " + url);

        return url;
    }

    /**
     * Builds the URL used to talk to the movie server using a location. This location is based
     * on the query capabilities of the movie provider that we are using.
     *
     * @param movieId movie id to get the trailers for.
     * @return The URL to use to query the movie server.
     */
    public static URL buildReviewsUrl (String movieId) {
        String trailerPath = "movie/" + movieId + "/reviews";//String.format(TRAILER_ENDPOINT, movieId);
        Uri builtUri = Uri.parse(INTRINIO_BASEPATH).buildUpon()
                .appendEncodedPath(trailerPath)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(PAGE_PARAM, String.valueOf(1))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built reviews URL: " + url);

        return url;
    }
}