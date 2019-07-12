package com.js.smart.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

public class PickerUtil {

    public static void setTimePickerDividerColor(int color, TimePicker timePicker) {
        /**
         * 设置时间选择器的分割线颜色
         */
        // 获取 mSpinners
        ViewGroup llFirst = (ViewGroup) timePicker.getChildAt(0);
        // 获取 NumberPicker
        ViewGroup mSpinners = (ViewGroup) llFirst.getChildAt(0);
        if (mSpinners.getChildCount() == 1)
            mSpinners = (ViewGroup) llFirst.getChildAt(1);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            if (mSpinners.getChildAt(i) instanceof NumberPicker) {//魅族可能为Textview
                NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

                Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                for (Field pf : pickerFields) {
                    if (pf.getName().equals("mSelectionDivider")) {
                        pf.setAccessible(true);
                        try {
                            pf.set(picker, new ColorDrawable(color));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 设置时间选择器的分割线颜色
     */
    public static void setDatePickerDividerColor(int color, DatePicker datePicker) {
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

    public static int getColor(Context context, int color) {
        return ContextCompat.getColor(context, color);
    }

}
