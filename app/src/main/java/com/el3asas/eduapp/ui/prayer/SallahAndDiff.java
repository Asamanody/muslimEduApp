package com.el3asas.eduapp.ui.prayer;

public class SallahAndDiff {
    private int i;
    private long diff;
    private long letfTime;
    public SallahAndDiff(int i, long diff,long letfTime) {
        this.i = i;
        this.diff = diff;
        this.letfTime=letfTime;
    }

    public int getI() {
        return i;
    }

    public long getDiff() {
        return diff;
    }

    public long getLetfTime() {
        return letfTime;
    }

    public void setLetfTime(long letfTime) {
        this.letfTime = letfTime;
    }
}
