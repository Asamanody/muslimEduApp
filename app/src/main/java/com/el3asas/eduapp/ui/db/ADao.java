package com.el3asas.eduapp.ui.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.el3asas.eduapp.ui.models.Entity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ADao {
    // with rxJava

    @Insert
    public Completable insertQr(Aentity entity);

    @Query("select * from A")
    public Observable<List<Entity>> getAllAr();


    @Query("update A set status =:s where num=:num")
    public Completable updateAStatusr(int num, int s);

    @Query("select * from A where num = :n")
    public Single<Entity> findAr(int n);

    @Query("select * from A where status =:s")
    public Flowable<List<Entity>> getAllLovedAr(int s);
}
