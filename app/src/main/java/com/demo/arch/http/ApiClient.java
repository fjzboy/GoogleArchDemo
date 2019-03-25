package com.demo.arch.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ApiClient {

    private static OkHttpClient okHttpClient;

    public static OkHttpClient instance() {
        if (okHttpClient == null) {
            synchronized (ApiClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

        return okHttpClient;
    }
}
