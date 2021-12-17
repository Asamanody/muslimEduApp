package com.el3asas.eduapp.ui.db;

import androidx.room.Entity;

@Entity(tableName = "H")
public class Hentity extends com.el3asas.eduapp.ui.models.Entity {
    public Hentity( String quot,String comment, String info, int num,int status) {
        super(quot,comment,info,num,status);
    }


    public int getStatus() {
        return super.getStatus();
    }

    public void setStatus(int status) {
        super.setStatus(status);
    }

    public String getQuot() {
        return super.getQuot();
    }

    public void setQuot(String quot) {
        super.setQuot(quot);
    }

    public String getInfo() {
        return super.getInfo();
    }

    public void setInfo(String info) {
        super.setInfo(info);
    }

    public int getNum() {
        return super.getNum();
    }

    public void setNum(int num) {
        super.setNum(num);
    }
    public String getComment(){
        return super.getComment();
    }
    public void setComment(String comment){
        super.setComment(comment);
    }
}
