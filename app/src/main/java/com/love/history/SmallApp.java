package com.love.history;

import android.app.Application;

import net.wequick.small.Small;

/**
 * The host application for Small
 */
public class SmallApp extends Application {

    public SmallApp() {
        Small.preSetUp(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Small.setLoadFromAssets(BuildConfig.LOAD_FROM_ASSETS);
    }
}