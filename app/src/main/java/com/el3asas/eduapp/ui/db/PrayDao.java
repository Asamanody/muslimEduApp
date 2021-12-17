package com.el3asas.eduapp.ui.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PrayDao {

    @Insert
    public Completable insertPray(PrayEntity prayEntity);

    @Query("select * from PrayEntity")
    public Observable<List<PrayEntity>> getAllPrays();

    @Update
    public Completable updatePray(PrayEntity prayEntity);
}
