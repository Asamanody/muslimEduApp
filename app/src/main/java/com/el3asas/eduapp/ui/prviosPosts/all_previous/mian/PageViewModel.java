package com.el3asas.eduapp.ui.prviosPosts.all_previous.mian;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.el3asas.eduapp.ui.models.Entity;
import com.el3asas.eduapp.ui.db.Repository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PageViewModel extends ViewModel {
    private static final String TAG = " ahmed PageViewModel";
    public static MutableLiveData<List<Entity>> qData=new MutableLiveData<>();
    public static MutableLiveData<List<Entity>> hData=new MutableLiveData<>();
    public static MutableLiveData<List<Entity>> aData=new MutableLiveData<>();
    private static final CompositeDisposable disposable=new CompositeDisposable();
    public static void getAllQ(){
        Repository instance = Repository.getInstance();
        instance.init();
        disposable.add(instance.getAllQr()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->{
                    qData.setValue(o);
                }));
    }

    public static void getAllH(){
        Repository instance = Repository.getInstance();
        instance.init();
        disposable.add(instance.getAllHr()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->{
                    hData.setValue(o);
                }));
    }

    public static void getAllA(){
        Repository instance = Repository.getInstance();
        instance.init();
        disposable.add(instance.getAllAr()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->{
                    aData.setValue(o);
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}