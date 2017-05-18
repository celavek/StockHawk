/*
 * Adapted from the Volley documentation at
 * https://developer.android.com/training/volley/requestqueue.html#singleton
 */
package com.udacity.stockhawk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Singleton instance to hold the Volley request queue and the Volley image loader.
 * The request queue will use the default Volley network and cache - DiskBasedCache.
 * The image loader will yse a LruCache to cache the downloaded images.
 */
public class VolleyController {

    /**
     * Default cache size for the image loader.
     */
    private static final int DEFAULT_CACHE_SIZE = 40;

    /**
     * Unique instance
     */
    private static VolleyController mInstance;

    /**
     * Volley request queue to handle network requests.
     */
    private RequestQueue mRequestQueue;

    /**
     * Volley image loader to handle remote image fetching.
     */
    private ImageLoader mImageLoader;

    /**
     * The application context required for proper lifetime and caching.
     */
    private static Context mContext;

    /**
     * Ctor.
     *
     * @param context application context
     */
    private VolleyController (Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(DEFAULT_CACHE_SIZE);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }


    /**
     * Get the static instance of the Volley controller.
     *
     * @param context android application context
     * @return
     */
    public static synchronized VolleyController getInstance (Context context) {
        if (mInstance == null) {
            mInstance = new VolleyController(context);
        }
        return mInstance;
    }


    /**
     * Get the Volley request queue.
     *
     * @return request queue
     */
    public RequestQueue getRequestQueue () {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }


    /**
     * Add a new request to the queue.
     *
     * @param req new request
     * @param <T> generic parameter type of the new request
     */
    public <T> void addToRequestQueue (Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Get the image loader.
     *
     * @return image loader with a LRU cache
     */
    public ImageLoader getImageLoader () {
        return mImageLoader;
    }
}