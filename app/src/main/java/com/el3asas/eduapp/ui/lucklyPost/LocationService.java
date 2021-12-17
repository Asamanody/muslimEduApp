package com.el3asas.eduapp.ui.lucklyPost;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service {

    private static final String NOTI_NAME = "LocationForgroundService";
    private static final String NOTIFICATION_CHANNEL_ID = "LocationService";
    private static final String TAG = "LocationService";
    public static boolean isRunning=false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning=true;
        startForeground(10, generateNotfication());
        Log.d(TAG, "onCreate: ++++++++++ssssssss+++++++++");
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = LocationRequest.create();
        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d(TAG, "onLocationResult: +++++++++++++++++++++++++++++++" + locationResult.getLastLocation().toString());
                LucklyFragment.longitude.setValue((double) locationResult.getLastLocation().getLongitude());
                LucklyFragment.latitude.setValue((double) locationResult.getLastLocation().getLatitude());
                stopSelf();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper());
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification generateNotfication() {
        NotificationHelper helper = new NotificationHelper(this);
        helper.setNotification(NOTIFICATION_CHANNEL_ID, NOTI_NAME, "اشعار تحديد الموقع", "يجرى التحقق من الموقع لتطبيق تزود لتحديد مواقيت الصلاه بشكل صحيح");
        return helper.getChannelNotification().build();
    }
}