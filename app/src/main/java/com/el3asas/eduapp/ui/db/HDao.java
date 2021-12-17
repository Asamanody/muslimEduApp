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
public interface HDao {

    // with rxJava

    @Insert
    public Completable insertHr(Hentity entity);

    @Query("select * from H")
    public Observable<List<Entity>> getAllHr();


    @Query("update H set status =:s where num=:num")
    public Completable updateHStatusr(int num, int s);

    @Query("select * from H where num = :n")
    public Single<Entity> findHr(int n);

    @Query("select * from H where status =:s")
    public Flowable<List<Entity>> getAllLovedHr(int s);
}
