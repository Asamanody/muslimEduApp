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
public interface QDao {

    // with rxJava

    @Insert
    public Completable insertQr(Qentity entity);

    @Query("select * from Q")
    public Observable<List<Entity>> getAllQr();


    @Query("update Q set status =:s where num=:num")
    public Completable updateQStatusr(int num, int s);

    @Query("select * from Q where num = :n")
    public Single<Entity> findQr(int n);

    @Query("select * from Q where status =:s")
    public Flowable<List<Entity>> getAllLovedQr(int s);
}
