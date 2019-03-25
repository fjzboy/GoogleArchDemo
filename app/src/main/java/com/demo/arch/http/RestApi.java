package com.demo.arch.http;

import com.demo.arch.bean.BaseBean;
import com.demo.arch.db.entity.News;

import java.util.List;

import io.reactivex.Completable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET(value = URL.NEWS_LIST)
    Call<BaseBean<List<News>>>
    reqNewsList(@Query("page") int page,
                @Query("size") int size);

    @GET(value = URL.NEWS_LIST_AT)
    Call<BaseBean<List<News>>>
    reqAfterNewsItem(@Query("position") int position,
                @Query("size") int size);


}
