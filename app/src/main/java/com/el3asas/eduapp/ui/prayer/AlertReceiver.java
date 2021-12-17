package com.el3asas.eduapp.ui.prayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int requestCode = intent.getIntExtra("requestCode", 0);
        setNextPrayAlarm(context, context.getSharedPreferences("Luckly", 0));
        playAzanWithNotification(context, requestCode);
    }

    private void playAzanWithNotification(Context context, int requestCode) {
        Intent intent = new Intent(context, Player.class);
        intent.putExtra("requestCode", requestCode);
        ContextCompat.startForegroundService(context, intent);
    }

    private void setNextPrayAlarm(Context context, SharedPreferences preferences) {
        PrayProperties.getInctance().setPrayProperaties(context, preferences);
    }
}