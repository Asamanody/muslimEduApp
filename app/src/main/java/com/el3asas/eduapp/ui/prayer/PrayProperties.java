package com.el3asas.eduapp.ui.prayer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.azan.Azan;
import com.azan.AzanTimes;
import com.azan.Method;
import com.azan.Time;
import com.azan.astrologicalCalc.Location;
import com.azan.astrologicalCalc.SimpleDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class PrayProperties {
    private static final String TAG = "PrayProperties";
    static PrayProperties prayProperties;

    public static PrayProperties getInctance() {
        if (prayProperties == null)
            prayProperties = new PrayProperties();
        return prayProperties;
    }

    public SallahAndDiff getSallahLoc(AzanTimes azanTimes) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        Time[] times = {azanTimes.fajr(), azanTimes.shuruq(), azanTimes.thuhr(),
                azanTimes.assr(), azanTimes.maghrib(), azanTimes.ishaa()};
        Calendar c1, c2;
        Date d1, d2;
        String t1, t2;
        for (int i = 0; i < times.length - 1; i++) {
            c1 = Calendar.getInstance();
            c1.set(Calendar.HOUR_OF_DAY, times[i].getHour());
            c1.set(Calendar.MINUTE, times[i].getMinute());
            c1.set(Calendar.SECOND, times[i].getSecond());

            c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, times[i + 1].getHour());
            c2.set(Calendar.MINUTE, times[i + 1].getMinute());
            c2.set(Calendar.SECOND, times[i + 1].getSecond());
            d1 = c1.getTime();
            t1 = sdf.format(d1);
            d2 = c2.getTime();
            t2 = sdf.format(d2);

            if (isTimeBetweenTwoTime(t1, t2, currentDateandTime)) {
                return new SallahAndDiff(i + 1
                        , getDiff2Time(d2.getTime(), d1.getTime()),
                        getDiff2Time(Calendar.getInstance().getTime().getTime(), c1.getTime().getTime()));
            }
        }
        c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, azanTimes.fajr().getHour());
        c1.set(Calendar.MINUTE, azanTimes.fajr().getMinute());
        c1.set(Calendar.SECOND, azanTimes.fajr().getSecond());
        c1.add(Calendar.DATE, 1);

        c2 = Calendar.getInstance();
        c2.set(Calendar.HOUR_OF_DAY, azanTimes.ishaa().getHour());
        c2.set(Calendar.MINUTE, azanTimes.ishaa().getMinute());
        c2.set(Calendar.SECOND, azanTimes.ishaa().getSecond());

        long d = getDiff2Time(Calendar.getInstance().getTime().getTime(), c2.getTime().getTime());
        if (d / (60 * 60 * 1000) > 9) {
            d = (24 * 60 * 60 * 1000) - d;
        }
        return new SallahAndDiff(0, getDiff2Time(c2.getTime().getTime(), c1.getTime().getTime()), d);
    }

    /***************************************************/

    public void setPrayProperaties(Context context, SharedPreferences preferences) {
        Log.d(TAG, "setPrayProperaties: +++++++++++++++");
        SimpleDate today = new SimpleDate(new GregorianCalendar());
        double longitude = Double.parseDouble(preferences.getString("longitude", ""));
        double latitude = Double.parseDouble(preferences.getString("latitude", ""));

        Location loc = new Location(latitude, longitude, 2.0, 0);
        Azan azan = new Azan(loc, Method.Companion.getEGYPT_SURVEY());
        AzanTimes prayerTimes = azan.getPrayerTimes(today);
        try {
            getNextPrayAlarm(context, preferences, prayerTimes);
        } catch (ParseException e) {
            Log.d("", "setPrayProp: ++++++++++" + e.getMessage());
        }
    }

    private void startAlarm(Context context, Calendar c, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("requestCode", requestCode);

        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent broadcast = PendingIntent.getBroadcast(context, requestCode
                , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), broadcast), broadcast);
    }

    private void getNextPrayAlarm(Context context, SharedPreferences preferences, AzanTimes azanTimes) throws ParseException {
        int nextPray = getNextPray(azanTimes);

        String[] prays = {"azanFajr", "azanShuruq", "azanZohr", "azanAssr", "azanMaghrib", "azanIshaa"};
        Time[] times = azanTimes.getTimes();
        for (int i = nextPray; i <= 5; i++) {
            if (preferences.getBoolean(prays[i], true)) {
                /*Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.MINUTE,5);
                startAlarm(context,calendar,i);*/
                startAlarm(context, getTimeCalender(times[i]), i);
                return;
            }
        }
        for (int i = 0; i < nextPray; i++) {
            if (preferences.getBoolean(prays[i], true)) {
                /*Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.MINUTE,5);
                startAlarm(context,calendar,i);*/
                startAlarm(context, getTimeCalender(times[i]), i);
                return;
            }
        }
    }

    private Calendar getTimeCalender(Time t) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, t.getHour());
        calendar.set(Calendar.MINUTE, t.getMinute());
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis())
            calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    /*********************************/

    private int getNextPray(AzanTimes azanTimes) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        Time[] times = azanTimes.getTimes();
        Calendar c1, c2;
        Date d1, d2;
        String t1, t2;
        for (int i = 0; i < times.length - 1; i++) {
            c1 = Calendar.getInstance();
            c1.set(Calendar.HOUR_OF_DAY, times[i].getHour());
            c1.set(Calendar.MINUTE, times[i].getMinute());
            c1.set(Calendar.SECOND, times[i].getSecond());

            c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, times[i + 1].getHour());
            c2.set(Calendar.MINUTE, times[i + 1].getMinute());
            c2.set(Calendar.SECOND, times[i + 1].getSecond());
            d1 = c1.getTime();
            t1 = sdf.format(d1);
            d2 = c2.getTime();
            t2 = sdf.format(d2);

            if (isTimeBetweenTwoTime(t1, t2, currentDateandTime)) {
                return i + 1;
            }
        }
        return 0;
    }

    private long getDiff2Time(long t1, long t2) {
        Log.d("", "getDiff2Time: " + (t1 - t2));
        return t1 - t2 > 0 ? t1 - t2 : -(t1 - t2);
    }

    private boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {
        Log.d("", "isTimeBetweenTwoTime: ttttttttttttt    " + initialTime + "     " + finalTime + "       " + currentTime);
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg) &&
                currentTime.matches(reg)) {
            //Start Time
            //all times are from java.util.Date
            @SuppressLint("SimpleDateFormat")
            Date inTime = new SimpleDateFormat("HH:mm").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            assert inTime != null;
            calendar1.setTime(inTime);

            //Current Time
            @SuppressLint("SimpleDateFormat")
            Date checkTime = new SimpleDateFormat("HH:mm").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            assert checkTime != null;
            calendar3.setTime(checkTime);

            //End Time
            @SuppressLint("SimpleDateFormat")
            Date finTime = new SimpleDateFormat("HH:mm").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            assert finTime != null;
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            Date actualTime = calendar3.getTime();
            return (actualTime.after(calendar1.getTime()) ||
                    actualTime.compareTo(calendar1.getTime()) == 0) &&
                    actualTime.before(calendar2.getTime());
        } else {
            Log.d("", "isTimeBetweenTwoTime: nottttttttttttt");
        }
        return false;
    }
}
