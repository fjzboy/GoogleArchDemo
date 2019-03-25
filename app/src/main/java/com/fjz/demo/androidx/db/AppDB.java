package com.fjz.demo.androidx.db;

import android.content.Context;

import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class AppDB {

    public static final String DB_NAME="room_demo.db";
    private static AppDatabase mInstance;

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (AppDB.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    System.out.println("app database is created:" +  + db.getVersion());
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    System.out.println("app database is opened!" + db.getVersion());
                                }
                            })
                            //.addMigrations()
                            .build();
                }
            }
        }
    }

    public static AppDatabase db() {
        if (mInstance == null) {
            Logger.i("db has been not initialized!!");
        }
        return mInstance;
    }
}
