package com.demo.arch.datasource;

import com.demo.arch.db.entity.News;
import com.demo.arch.repository.NewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

public class NewsDataSource extends PageKeyedDataSource<Integer, News> {

    private NewsRepository repository;

    public NewsDataSource(NewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, News> callback) {

        int size = params.requestedLoadSize;
        try {
            List<News> list = repository.reqNewsList(1, size);
            callback.onResult(list, null, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, News> callback) {

        int size = params.requestedLoadSize;
        int page = params.key;
        try {
            List<News> list = repository.reqNewsList(page, size);
            callback.onResult(list, page-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, News> callback) {
        int size = params.requestedLoadSize;
        int page = params.key;
        try {
            List<News> list = repository.reqNewsList(page, size);
            callback.onResult(list, page+1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
