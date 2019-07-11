package com.js.smart.ui.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.DateUtil;
import com.js.smart.ui.R;

import java.lang.reflect.Field;


/**
 * Created by Js on 2016/6/22.
 */
public class DateTimePickerDialog extends DialogBuilder<DateTimePickerDialog> {

    public DateTimePickerDialog(Context context) {
        super(context);
    }

    private String date;
    private String time;

    /**
     * date样式
     */
    public DateTimePickerDialog showDateTimePickerDialog(String date, AntiShakeOnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_date_time,null);
        TextView titleTv =  view.findViewById(R.id.textView1);
        datePicker = view.findViewById(R.id.datePicker1);
        timePicker = view.findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);
        titleTv.setText(title);
        if (!TextUtils.isEmpty(date)) {
            int year = Integer.valueOf(date.substring(0, 4));
            int month = Integer.valueOf(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
            int day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1, date.indexOf(" ")));
            datePicker.init(year, month - 1, day, dateChangedListener);
            int hour = Integer.valueOf(date.substring(date.indexOf(" ") + 1, date.indexOf(":")));
            int minute = Integer.valueOf(date.substring(date.indexOf(":") + 1, date.lastIndexOf(":") == -1? date.length():date.lastIndexOf(":")));
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        } else {
            datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), dateChangedListener);
        }
        this.date = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
        time = " "+ DateUtil.getCurrentDate("HH:mm");
        result = this.date + time;

        timePicker.setOnTimeChangedListener(timeChangedListener);
        setDatePickerDividerColor(ContextCompat.getColor(context, R.color.colorPrimary), datePicker);
        setDatePickerDividerColor(ContextCompat.getColor(context, R.color.colorPrimary), timePicker);
        TextView rightBt = view.findViewById(R.id.btn2);
        TextView leftBt = view.findViewById(R.id.btn1);
        leftBt.setOnClickListener(listener);
        rightBt.setOnClickListener(listener);
        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)) {
            rightBt.setText(rightStr);
        }
        setDialog(show());
        return this;
    }

    private DatePicker datePicker;
    private DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
            result = date + time;
        }
    };


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
            time = " " + h + ":" + m;
            result = date + time;
        }
    };


    /**
     * 设置时间选择器的分割线颜色
     */
    public void setDatePickerDividerColor(int color, FrameLayout datePicker) {
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);
        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
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
