package com.js.smart.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.TimePicker;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.DateUtil;
import com.js.smart.common.util.PickerUtil;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class TimePickerDialog extends DialogBuilder<TimePickerDialog> {

    private TimePicker timePicker;
    private TextView titleTv;
    private TextView rightBt;
    private TextView leftBt;

    public TimePickerDialog(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_time, null);
        titleTv = view.findViewById(R.id.textView1);
        timePicker = view.findViewById(R.id.timePicker1);
        PickerUtil.setTimePickerDividerColor(PickerUtil.getColor(context, R.color.colorPrimary), timePicker);
        rightBt = view.findViewById(R.id.btn2);
        leftBt = view.findViewById(R.id.btn1);
    }

    /**
     * time样式
     */
    public TimePickerDialog showTimePickerDialog(String time, AntiShakeOnClickListener listener) {
        timePicker.setOnTimeChangedListener(timeChangedListener);
        timePicker.setIs24HourView(true);
        titleTv.setText(title);
        if (!TextUtils.isEmpty(time)) {
            timePicker.setCurrentHour(Integer.valueOf(time.substring(0, time.indexOf(":"))));
            timePicker.setCurrentMinute(Integer.valueOf(time.substring(time.indexOf(":") + 1, time.lastIndexOf(":") == -1 ? time.length() : time.lastIndexOf(":"))));
        } else {
            result = DateUtil.getCurrentDate("HH:mm");
        }

        if (listener != null)
            setLeftRightClick(listener, listener);
        leftBt.setOnClickListener(leftClickListener);
        rightBt.setOnClickListener(rightClickListener);

        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)) {
            rightBt.setText(rightStr);
        }
        setDialog(show());

        return this;
    }

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


}
