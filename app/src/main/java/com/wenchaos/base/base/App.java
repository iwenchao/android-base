package com.wenchaos.base.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import org.androidannotations.annotations.EApplication;

/**
 * Created by Administrator
 * on 2016/6/27 0027.
 */
@EApplication
public class App extends Application {

    public static App mApp;
    public static Resources mResources;

    @Override
    public void onCreate() {
        super.onCreate();


    }

    public static App getGlobalApp() {
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }
}
