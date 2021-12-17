package com.el3asas.eduapp.ui.prayer;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.el3asas.eduapp.ui.lucklyPost.MainActivity2;
import com.el3asas.eduapp.ui.lucklyPost.NotificationHelper;

public class PrayerBGS extends Service {

    private static final String channelId = "prayerBGS";
    private static final String channelName = "prayerBGS";
    public static boolean playing_flag = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playing_flag = true;
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.setNotification(channelId, channelName, "تطبيق تزود", "يعمل تطبيق تزود فى الخلفيه للحفاظ على التنبيه على الاذان فى مواقيت الصلاه .");
        Intent intent1 = new Intent(this, MainActivity2.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        startForeground(2, notificationHelper.getChannelNotification()
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .build());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}