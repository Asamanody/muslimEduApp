package com.el3asas.eduapp.ui.lucklyPost;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.el3asas.eduapp.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void loveBtn(View btn) {
        if (((CheckBox) btn).isChecked()) {
            LuckilyPostViewModel.update(1);
        } else {
            LuckilyPostViewModel.update(0);
        }
        setAnimation(btn);
    }

    private static void setAnimation(View v) {
        final ValueAnimator anim = ValueAnimator.ofFloat(1f, .7f);
        anim.setDuration(150);
        anim.addUpdateListener(animation -> {
            v.setScaleX((Float) animation.getAnimatedValue());
            v.setScaleY((Float) animation.getAnimatedValue());
        });
        anim.setRepeatCount(1);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
    }
    public void loveZBtn(View btn) {
        /*if (((CheckBox) btn).isChecked()) {
            LuckilyPostViewModel.update(1);
        } else {
            LuckilyPostViewModel.update(0);
        }*/
        setAnimation(btn);
    }



}