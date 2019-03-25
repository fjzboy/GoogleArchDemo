package com.demo.arch.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {

    private static Retrofit mRetrofit;
    private static OkHttpClient mClient;
    private static RestApi mRestApi;

    public static Retrofit instance() {

        mClient = ApiClient.instance();


        if (mRetrofit == null) {
            synchronized (HttpService.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(URL.BASE)
                            .client(mClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    public static RestApi getService() {
        if (mRestApi == null) {
            synchronized (HttpService.class) {
                if (mRestApi == null) {
                    mRestApi = instance().create(RestApi.class);
                }
            }
        }
        return mRestApi;
    }

}
