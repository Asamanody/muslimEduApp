package com.el3asas.eduapp.ui.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface AzkarDao {
    @Insert
    Completable insertAzkar(List<AzkarEntity> azkarEntities);

    @Query("select *from azkar")
    Observable<List<AzkarEntity>> getAllAzkar();

    @Query("select *from azkar where category =:category")
    Observable<List<AzkarEntity>> getAzkarByCat(String category);
}
