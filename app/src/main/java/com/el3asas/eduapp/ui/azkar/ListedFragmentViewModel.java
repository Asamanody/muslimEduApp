package com.el3asas.eduapp.ui.azkar;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.el3asas.eduapp.ui.db.AzkarEntity;
import com.el3asas.eduapp.ui.db.Repository;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListedFragmentViewModel extends ViewModel {
    MutableLiveData<List<AzkarEntity>> liveData = new MutableLiveData<>();

    @SuppressLint("NotifyDataSetChanged")
    public MutableLiveData<List<AzkarEntity>> getAllAzkar(String[] cat) {
        for (String c : cat) {
            Repository repository = Repository.getInstance();
            repository.init();
            repository.getAzkarByCat(c).observeOn(Schedulers.io()).subscribeOn(Schedulers.computation())
                    .subscribe(azkarEntities -> {
                        liveData.postValue(azkarEntities);
                        Log.d("TAG", "getAllAzkar: --------------" + azkarEntities.get(0).getZekr());
                    }, throwable -> Log.d("TAG", "getAllAzkar: ------" + throwable.getMessage()));
        }
        return liveData;
    }
}