package com.el3asas.eduapp.ui.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Aentity.class, Hentity.class, Qentity.class, PrayEntity.class}, version = 12)
public abstract class DataBase extends RoomDatabase {

    public static DataBase instance;

    public abstract QDao qDao();

    public abstract HDao hDao();

    public abstract ADao aDao();

    public abstract PrayDao prayDao();

    public static synchronized void getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "myDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static DataBase getInstance() {
        return instance;
    }
}