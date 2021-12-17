package com.el3asas.eduapp.ui.lucklyPost;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.ui.prayer.PrayItem;

import java.util.Calendar;

public class AddAarmDialog extends Dialog implements android.view.View.OnClickListener {

    private final PrayItem item=new PrayItem();
    private boolean inSecondView = false;
    private Button btn;
    private NumberPicker h, m, am_pm;
    private LinearLayout picker;

    public AddAarmDialog(@NonNull Context context) {
        super(context);
    }

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_new_pray);
        editText = findViewById(R.id.value);
        btn = findViewById(R.id.next);

        h = findViewById(R.id.hours);
        m = findViewById(R.id.minutes);
        am_pm = findViewById(R.id.am_pm);

        h.setMaxValue(12);
        h.setMinValue(1);
        m.setMaxValue(59);
        m.setMinValue(0);
        am_pm.setMinValue(0);
        am_pm.setMaxValue(1);
        picker=findViewById(R.id.piker);
        am_pm.setFormatter(value -> {
            switch (value) {
                case 0:
                    return "AM";
                case 1:
                    return "BM";
            }
            return null;
        });
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!inSecondView) {
            if (editText.getText().toString().equals("")) {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.aleartAboutAlarmName), Toast.LENGTH_LONG).show();
                return;
            }
            item.name = editText.getText().toString();
            picker.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            btn.setText(R.string.creatAlarm);
            inSecondView=true;
        } else {
            Calendar c=Calendar.getInstance();

            c.set(Calendar.HOUR, h.getValue());
            c.set(Calendar.MINUTE, m.getValue());
            c.set(Calendar.AM_PM, am_pm.getValue());
            item.calendar=c;
            dismiss();
            Toast.makeText(getContext(),"تم اضافه التنبيه",Toast.LENGTH_SHORT).show();
        }
    }

    public PrayItem getPrayItem() {
        return item;
    }
}