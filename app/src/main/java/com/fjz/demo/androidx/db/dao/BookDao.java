package com.fjz.demo.androidx.db.dao;

import com.fjz.demo.androidx.db.entity.Book;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAllBooks();

    @Insert
    void insertAll(List<Book> all);
}
