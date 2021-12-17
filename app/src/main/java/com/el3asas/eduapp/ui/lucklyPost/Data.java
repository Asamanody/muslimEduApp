package com.el3asas.eduapp.ui.lucklyPost;

import com.el3asas.eduapp.ui.db.AzkarEntity;

import java.util.List;

public class Data {
    private List<AzkarEntity> data;

    public Data(List<AzkarEntity> data) {
        this.data = data;
    }

    public List<AzkarEntity> getData() {
        return data;
    }

    public void setData(List<AzkarEntity> data) {
        this.data = data;
    }
}
