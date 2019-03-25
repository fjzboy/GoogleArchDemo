package com.demo.arch.datasource;

import com.demo.arch.db.entity.News;
import com.demo.arch.repository.NewsRepository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class NewsDataSourceFactory extends DataSource.Factory<Integer, News> {
    @NonNull
    @Override
    public DataSource<Integer, News> create() {
        return new NewsDataSource(new NewsRepository());
    }

}
