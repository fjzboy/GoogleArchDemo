package com.demo.arch.db.dao;

import com.demo.arch.db.entity.News;

import java.util.List;

import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NewsDao {

    @Insert
    void insertAll(List<News> news);

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news")
    PageKeyedDataSource.Factory<Integer, News> getAllNews();

    @Query("DELETE FROM news")
    void clear();
}
