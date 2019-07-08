package com.js.smart.ui.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthPopupWindow extends TwoWheelPopupWindow<MonthPopupWindow> {

    protected int year = 50;
    protected int month = 12;
    protected int currentYear;
    protected int currentMonth;
    protected boolean ase;

    public MonthPopupWindow(Context context, View view, String monthStr, String yearStr) {
        this(context, view, monthStr, yearStr, false);
    }

    public MonthPopupWindow(Context context, View view, String monthStr, String yearStr, boolean ase) {
        super(context, view);
        this.ase = ase;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse("2019-12-2"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        if (StringUtils.isBlank(yearStr))
            yearStr = String.valueOf(currentYear);
        setItemsTwo(getYear(year), currentYear - Integer.valueOf(yearStr));
        if (StringUtils.isBlank(monthStr))
            monthStr = "01";
//        if (Integer.valueOf(yearStr) == currentYear) {
//            setItemsOne(getMonth(currentMonth), 0);
//        } else {
            setItemsOne(getMonth(month), Integer.valueOf(monthStr) - 1);
//        }

//        wheelView2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//            @Override
//            public void onSelected(int selectedIndex, String item) {
//                super.onSelected(selectedIndex, item);
//                if (Integer.valueOf(item) == currentYear) {
//                    setItemsOne(getMonth(currentMonth), 0);
//                } else {
//                    setItemsOne(getMonth(month), wheelView1.getSeletedIndex());
//                }
//            }
//        });
    }

    private List<String> getMonth(int month) {
        List<String> list = new ArrayList<>();
//        if (!ase)
            for (int i = 1; i <= month; i++) {
                list.add(String.format("%02d", i));
            }
//        else
//            for (int i = month; i <= 12; i++) {
//                list.add(String.format("%02d", i));
//            }
        return list;
    }

    private List<String> getYear(int year) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < year; i++) {
            if (!ase)
                list.add(String.valueOf(currentYear - i));
            else
                list.add(String.valueOf(currentYear + i));
        }
        return list;
    }

    @Override
    public String getSelectedItemOne() {
        return String.format("%02d", Integer.valueOf(super.getSelectedItemOne()));
    }

    public PopupWindow show(MonthPopupListener listener) {
        btn2.setOnClickListener(v -> {
            listener.item(getSelectedItemOne(), getSelectedItemTwo());
            windowUtil.dismiss();
        });
        return windowUtil.setObscure().showAtLocation(Gravity.BOTTOM, 0, 0);
    }

    public interface MonthPopupListener {
        void item(String month, String year);
    }

}
