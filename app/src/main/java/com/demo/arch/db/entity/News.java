package com.demo.arch.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class News {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String subTitle;
    private long date;
    private String author;

    public News() {
    }
    @Ignore
    public News(Long id, String title, String subTitle, long date, String author) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.date = date;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", date=" + date +
                ", author='" + author + '\'' +
                '}';
    }
}
