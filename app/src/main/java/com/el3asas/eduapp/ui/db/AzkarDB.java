package com.el3asas.eduapp.ui.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AzkarEntity.class}, version = 1)
public abstract class AzkarDB extends RoomDatabase {

    public static AzkarDB instance;

    public abstract AzkarDao azkarDao();

    public static synchronized void getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AzkarDB.class, "myAzkarDB")
                    .createFromAsset("databases/myAzkarDB.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static AzkarDB getInstance() {
        return instance;
    }
}