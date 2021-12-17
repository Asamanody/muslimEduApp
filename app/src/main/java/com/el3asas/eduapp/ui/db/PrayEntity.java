package com.el3asas.eduapp.ui.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PrayEntity")
public class PrayEntity {
    @PrimaryKey
    private int prayNum;
    private long prayTime;
    private boolean status;

    public int getPrayNum() {
        return prayNum;
    }

    public void setPrayNum(int prayNum) {
        this.prayNum = prayNum;
    }

    public long getPrayTime() {
        return prayTime;
    }

    public void setPrayTime(long prayTime) {
        this.prayTime = prayTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
