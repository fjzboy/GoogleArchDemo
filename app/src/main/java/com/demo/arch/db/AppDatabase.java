package com.demo.arch.db;

import com.demo.arch.db.dao.BookDao;
import com.demo.arch.db.dao.NewsDao;
import com.demo.arch.db.entity.Book;
import com.demo.arch.db.entity.News;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        Book.class,
        News.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    public abstract NewsDao newsDao();

}
