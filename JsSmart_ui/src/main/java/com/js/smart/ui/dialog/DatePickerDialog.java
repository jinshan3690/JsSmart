package com.js.smart.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.TextView;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.DateUtil;
import com.js.smart.common.util.PickerUtil;
import com.js.smart.ui.R;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Js on 2016/6/22.
 */
public class DatePickerDialog extends DialogBuilder<DatePickerDialog> {

    public DatePickerDialog(Context context) {
        super(context);
    }

    /**
     * date样式
     */
    public DatePickerDialog showDatePickerDialog(String date, AntiShakeOnClickListener listener) {
        int year = 0, month = 0, day = 0;
        if (StringUtils.isNotBlank(date)) {
            if (date.length() >= 4)
                year = Integer.valueOf(date.substring(0, 4));
            if (date.length() >= 7)
                month = Integer.valueOf(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
            if (date.length() >= 10)
                day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1));
        }
        view = LayoutInflater.from(context).inflate(R.layout.dialog_date, null);
        TextView titleTv = view.findViewById(R.id.textView1);
        datePicker = view.findViewById(R.id.datePicker1);
        titleTv.setText(title);
        if (year != 0 && month != 0 && day != 0)
            datePicker.init(year, month - 1, day, dateChangedListener);
        else {
            datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), dateChangedListener);
        }
        result = DateUtil.getDate(DateUtil.toTime(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth()));

        PickerUtil.setDatePickerDividerColor(PickerUtil.getColor(context, R.color.colorPrimary), datePicker);

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
            result = DateUtil.getDate(DateUtil.toTime(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth()));
        }
    };


}
