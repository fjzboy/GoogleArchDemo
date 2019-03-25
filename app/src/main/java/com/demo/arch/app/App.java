package com.demo.arch.app;

import android.app.Application;

import com.demo.arch.db.AppDB;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class App extends Application {

    private FirebaseAnalytics mAnalytics;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());

        AppDB.init(getApplicationContext());

        mAnalytics = FirebaseAnalytics.getInstance(this);

    }

    public FirebaseAnalytics getAnalytics() {
        return mAnalytics;
    }
}
