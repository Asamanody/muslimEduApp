package com.el3asas.eduapp.ui.prayer;

import java.util.Calendar;

public class PrayItem {
    public String name;
    public Calendar calendar;

    public PrayItem() {
    }

    public PrayItem(String name, Calendar calendar) {
        this.name = name;
        this.calendar=calendar;
    }
}
