package com.js.smart.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.DatePicker;
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
        PickerUtil.setDatePickerDividerColor(PickerUtil.getColor(context, R.color.colorPrimary), datePicker);
        PickerUtil.setTimePickerDividerColor(PickerUtil.getColor(context, R.color.colorPrimary), timePicker);
        TextView rightBt = view.findViewById(R.id.btn2);
        TextView leftBt = view.findViewById(R.id.btn1);
        if(listener != null)
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




}
