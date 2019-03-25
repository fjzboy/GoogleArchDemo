package com.fjz.demo.androidx.app;

import android.app.Application;

import com.fjz.demo.androidx.db.AppDB;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

public class App extends Application {

    private FirebaseAnalytics mAnalytics;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        MMKV.initialize(this);
        AppDB.init(getApplicationContext());

        mAnalytics = FirebaseAnalytics.getInstance(this);

    }

    public FirebaseAnalytics getAnalytics() {
        return mAnalytics;
    }
}

//c4SSReF5mKU:APA91bEZOH8barloNegdBvO32q8atdbPmudnz7gJ_TQpQfsDyD1eNgv6WYU8CpNbz5zIcNFnimKx371ARmIL5HvdSjV5HrPMdXBwrJWtiHWRl0Ix9-qdV_p_YnY5tXlz7ay2nOwdvqrA
//c4SSReF5mKU:APA91bEZOH8barloNegdBvO32q8atdbPmudnz7gJ_TQpQfsDyD1eNgv6WYU8CpNbz5zIcNFnimKx371ARmIL5HvdSjV5HrPMdXBwrJWtiHWRl0Ix9-qdV_p_YnY5tXlz7ay2nOwdvqrA
