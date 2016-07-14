package com.wenchaos.mxnews.data;

import com.google.gson.Gson;

/**
 * Created by Administrator
 * on 2016/6/27 0027.
 */
public class GsonProvider {
    private static GsonProvider INSTANCE = null;
    private static Gson mGson = null;

    private GsonProvider() {
        mGson = new Gson();
    }

    public static GsonProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GsonProvider();
        }
        return INSTANCE;
    }

    public static Gson getGson() {
        return mGson == null ? new Gson() : mGson;
    }
}
