package com.fjz.demo.androidx.datasource;

import com.fjz.demo.androidx.db.entity.News;
import com.fjz.demo.androidx.repository.NewsRepository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class NewsDataSourceFactory extends DataSource.Factory<Integer, News> {
    @NonNull
    @Override
    public DataSource<Integer, News> create() {
        return new NewsDataSource(new NewsRepository());
    }

}
