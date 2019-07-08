package com.js.smart.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.DateUtil;
import com.js.smart.ui.R;

import java.lang.reflect.Field;


/**
 * Created by Js on 2016/6/22.
 */
public class TimePickerDialog extends DialogBuilder<TimePickerDialog> {

    public TimePickerDialog(Context context) {
        super(context);
    }

    /**
     * time样式
     */
    public Dialog showTimePickerDialog(String time, View.OnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_time, null);
        TextView titleTv = (TextView) view.findViewById(R.id.textView1);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker1);

        timePicker.setOnTimeChangedListener(timeChangedListener);
        timePicker.setIs24HourView(true);
        titleTv.setText(title);
        if (!TextUtils.isEmpty(time)) {
            timePicker.setCurrentHour(Integer.valueOf(time.substring(0, time.indexOf(":"))));
            timePicker.setCurrentMinute(Integer.valueOf(time.substring(time.indexOf(":") + 1, time.length())));
        } else {
            result = DateUtil.getCurrentDate("HH:mm");
        }

        setDatePickerDividerColor(ContextCompat.getColor(context, R.color.colorPrimary), timePicker);
        Button rightBt = (Button) view.findViewById(R.id.btn2);
        Button leftBt = (Button) view.findViewById(R.id.btn1);
        leftBt.setOnClickListener(listener);
        rightBt.setOnClickListener(listener);
        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)) {
            rightBt.setText(rightStr);
        }
        return show();
    }

    private TimePicker timePicker;
    private TimePicker.OnTimeChangedListener timeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            String h = String.valueOf(hourOfDay);
            String m = String.valueOf(minute);
            if (hourOfDay < 10) {
                h = "0" + hourOfDay;
            }
            if (minute < 10) {
                m = "0" + minute;
            }
            result = h + ":" + m;
        }
    };

    /**
     * 设置时间选择器的分割线颜色
     */
    public void setDatePickerDividerColor(int color, FrameLayout datePicker) {
        // 获取 mSpinners
        ViewGroup llFirst = (ViewGroup) datePicker.getChildAt(0);
        // 获取 NumberPicker
        ViewGroup mSpinners = (ViewGroup) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            if (mSpinners.getChildAt(i) instanceof NumberPicker) {//魅族可能为Textview
                NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

                Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                for (Field pf : pickerFields) {
                    if (pf.getName().equals("mSelectionDivider")) {
                        pf.setAccessible(true);
                        try {
                            pf.set(picker, new ColorDrawable(color));
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }


}
