package com.el3asas.eduapp.ui.prviosPosts.all_previous.mian;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    public MutableLiveData<String> searchQueryLiveData = new MutableLiveData<>();

    public void setData(String str) {
        this.searchQueryLiveData.setValue(str);
    }

    @Override
    public void onCleared() {
        super.onCleared();
    }
}