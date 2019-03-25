package com.fjz.demo.androidx.repository;

import com.fjz.demo.androidx.bean.BaseBean;
import com.fjz.demo.androidx.db.entity.News;
import com.fjz.demo.androidx.exception.ApiException;
import com.fjz.demo.androidx.http.HttpService;
import com.orhanobut.logger.Logger;

import java.util.List;

public class NewsRepository {

    public List<News> reqNewsList(int page, int size) throws Exception {
        List<News> list = null;
        BaseBean<List<News>> result = HttpService.getService().reqNewsList(page, size).execute().body();
        if (result != null && result.getCode() == 0) {
            list = result.getData();
        } else {
            throw new ApiException(result.getCode());
        }
        return list;
    }

    public List<News> reqAfterNewsList(int position, int size) throws Exception {

        Logger.i("request after news : pos = %d, size = %d", position, size);
        List<News> list = null;
        BaseBean<List<News>> result = HttpService.getService().reqAfterNewsItem(position, size).execute().body();
        if (result != null && result.getCode() == 0) {
            list = result.getData();
        } else {
            throw new ApiException(result.getCode());
        }
        return list;
    }
}
