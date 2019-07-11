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

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.DateUtil;
import com.js.smart.ui.R;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

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
        setDatePickerDividerColor(ContextCompat.getColor(context, R.color.colorPrimary), datePicker);
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
            result = DateUtil.getDate(DateUtil.toTime(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth()));
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
