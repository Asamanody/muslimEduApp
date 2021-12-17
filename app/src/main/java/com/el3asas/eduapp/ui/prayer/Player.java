package com.el3asas.eduapp.ui.prayer;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.ui.lucklyPost.NotificationHelper;

import java.util.Calendar;

public class Player extends Service {
    private static final String channelId = "forGroundNoti";
    private static final String channelName = "PlayerChannel";
    private MediaPlayer mediaPlayer;

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra("cancel", false) && mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            stopSelf();
        } else {
            NotificationHelper notificationHelper = new NotificationHelper(this);
            int i = intent.getIntExtra("requestCode", 0);
            switch (i) {
                case 0:
                    notificationHelper.setNotification(channelId, channelName, "صلاه الفجر", "حان الان موعد اذان صلاه الفجر");
                    break;
                case 1:
                    notificationHelper.setNotification(channelId, channelName, "صلاه الشروق", "حان الان موعد صلاه الشروق");
                    break;
                case 2:
                    Calendar calendar = Calendar.getInstance();
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                        notificationHelper.setNotification(channelId, channelName, "صلاه الجمعه", "حان الان موعد اذان صلاه الجمعه");
                    else
                        notificationHelper.setNotification(channelId, channelName, "صلاه الظهر", "حان الان موعد اذان صلاه الظهر");
                    break;
                case 3:
                    notificationHelper.setNotification(channelId, channelName, "صلاه العصر", "حان الان موعد اذان صلاه العصر");
                    break;
                case 4:
                    notificationHelper.setNotification(channelId, channelName, "صلاه المغرب", "حان الان موعد اذان صلاه المغرب");
                    break;
                case 5:
                    notificationHelper.setNotification(channelId, channelName, "صلاه العشاء", "حان الان موعد اذان صلاه العشاء");
                    break;

            }
            Intent intent1 = new Intent(this, WakeAlarmActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("requestCode", i);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, i, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent cancelIntent = new Intent(this, Player.class);

            PendingIntent cancelMediaPlayer = PendingIntent.getService(this, i, cancelIntent.putExtra("cancel", true), PendingIntent.FLAG_UPDATE_CURRENT);

            if (i == 0)
                mediaPlayer = MediaPlayer.create(this, R.raw.afasy);
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.afasyd);

            mediaPlayer.start();
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(.7f, .7f);

            mediaPlayer.setOnCompletionListener(mp -> {
                if (WakeAlarmActivity.close != null) {
                    WakeAlarmActivity.close.setValue(true);
                    stopSelf();
                }
            });

            startForeground(1, notificationHelper.getChannelNotification()
                    .setFullScreenIntent(pendingIntent, true)
                    .addAction(R.drawable.ic_app_ic, getString(R.string.cancel_media_player), cancelMediaPlayer)
                    .build());
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer = null;
    }
}