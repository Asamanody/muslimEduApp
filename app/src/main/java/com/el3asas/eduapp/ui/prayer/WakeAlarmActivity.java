package com.el3asas.eduapp.ui.prayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.ActivityWakeAlarmBinding;

public class WakeAlarmActivity extends AppCompatActivity {
    public static MutableLiveData<Boolean> close= new MutableLiveData<>();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        close.setValue(false);
        ActivityWakeAlarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_wake_alarm);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        int requestCode = getIntent().getIntExtra("requestCode", 0);
        String prayName="";
        switch (requestCode) {
            case 0:
                prayName = getString(R.string.fajr);
                break;
            case 1:
                prayName = getString(R.string.shruq);
                break;
            case 2:
                prayName= getString(R.string.zohr);
                break;
            case 3:
                prayName= getString(R.string.assr);
                break;
            case 4:
                prayName= getString(R.string.maaghreb);
                break;
            case 5:
                prayName= getString(R.string.asha);
                break;
        }

        binding.prayName.setText(prayName);
        binding.closeBtn.setOnClickListener(v -> {
            Intent intent=new Intent(WakeAlarmActivity.this,Player.class);
            intent.putExtra("cancel",true);
            ContextCompat.startForegroundService(WakeAlarmActivity.this,intent);
            finish();
        });
        close.observe(this, aBoolean -> {
            if (aBoolean)
                finish();
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        } else
            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

}