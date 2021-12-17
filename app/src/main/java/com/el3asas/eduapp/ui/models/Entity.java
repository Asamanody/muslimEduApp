package com.el3asas.eduapp.ui.models;

import androidx.annotation.Keep;
import androidx.room.PrimaryKey;

@Keep
public class Entity {

    private int status = 0;
    private String quot;
    private String info;
    private String comment;
    @PrimaryKey
    private int num;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Entity() {
    }

    public Entity(String quot,String comment, String info, int num, int status) {
        this.status = status;
        this.quot = quot;
        this.info = info;
        this.num = num;
        this.comment=comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQuot() {
        return quot;
    }

    public void setQuot(String quot) {
        this.quot = quot;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
