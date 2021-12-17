package com.el3asas.eduapp.ui.azkar;

public class MainAzkarItem {

    private int imgRes;
    private String azkarName;

    public MainAzkarItem(int imgRes, String azkarName) {
        this.imgRes = imgRes;
        this.azkarName = azkarName;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getAzkarName() {
        return azkarName;
    }

    public void setAzkarName(String azkarName) {
        this.azkarName = azkarName;
    }
}