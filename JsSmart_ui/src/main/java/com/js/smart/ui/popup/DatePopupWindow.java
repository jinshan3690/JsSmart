package com.js.smart.ui.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.js.smart.common.ui.popup.BasePopupWindow;
import com.js.smart.ui.R;
import com.js.smart.ui.widget.WheelView;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatePopupWindow extends BasePopupWindow<DatePopupWindow> {

    protected TextView titleTv;
    protected WheelView yearWl;
    protected WheelView monthWl;
    protected WheelView dayWl;
    protected WheelView hourWl;
    protected WheelView minuteWl;
    protected WheelView secondWl;
    protected TextView confirm;

    protected SimpleDateFormat sdf;
    protected int yearDiff = 50;
    protected int currentYear;
    protected int currentMonth;
    protected int currentDayOfMonth;
    protected int currentDay;
    protected int currentHour;
    protected int currentMinute;
    protected int currentSecond;
    protected Boolean beforeOfAfter;
    private WheelView.OnWheelViewListener yearWheelViewListener;

    protected int chooseYear = 0, chooseMonth = 0, chooseDay = 0, chooseHour = 0, chooseMinute = 0, chooseSecond = 0;
    protected Integer chooseYearIndex, chooseMonthIndex, chooseDayIndex, chooseHourIndex, chooseMinuteIndex, chooseSecondIndex;

    public DatePopupWindow(Context context, View view, String chooseDate) {
        this(context, view, chooseDate, null, null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public DatePopupWindow(Context context, View view, String chooseDate, SimpleDateFormat dateFormat) {
        this(context, view, chooseDate, null, null, dateFormat);
    }

    public DatePopupWindow(Context context, View view, String chooseDate, boolean beforeOfAfter) {
        this(context, view, chooseDate, beforeOfAfter, null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public DatePopupWindow(Context context, View view, String chooseDate, Boolean beforeOfAfter, String beforeOfAfterStr) {
        this(context, view, chooseDate, beforeOfAfter, beforeOfAfterStr, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public DatePopupWindow(Context context, View view, String chooseDate, Boolean beforeOfAfter, String beforeOfAfterStr, SimpleDateFormat dateFormat) {
        super(context, view, R.layout.pop_date_wheel);
        this.beforeOfAfter = beforeOfAfter;
        this.sdf = dateFormat;

        initView();

        Calendar calendar = Calendar.getInstance();

        if (StringUtils.isNotBlank(beforeOfAfterStr)) {
            try {
                calendar.setTime(sdf.parse(beforeOfAfterStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        currentSecond = calendar.get(Calendar.SECOND);
        currentDayOfMonth = getCurrentDayOfMonth();

        Calendar chooseCalendar = Calendar.getInstance();

        if (StringUtils.isBlank(chooseDate))
            chooseDate = sdf.format(calendar.getTime());
        if (StringUtils.isNotBlank(chooseDate)) {
            try {
                chooseCalendar.setTime(sdf.parse(chooseDate));
                if (beforeOfAfter == null || (beforeOfAfter && chooseCalendar.getTimeInMillis() <= calendar.getTimeInMillis()) ||
                        (!beforeOfAfter && chooseCalendar.getTimeInMillis() >= calendar.getTimeInMillis())) {

                    chooseYear = chooseCalendar.get(Calendar.YEAR);
                    chooseMonth = chooseCalendar.get(Calendar.MONTH) + 1;
                    chooseDay = chooseCalendar.get(Calendar.DAY_OF_MONTH);
                    chooseHour = chooseCalendar.get(Calendar.HOUR_OF_DAY);
                    chooseMinute = chooseCalendar.get(Calendar.MINUTE);
                    chooseSecond = chooseCalendar.get(Calendar.SECOND);
                    currentDayOfMonth = getChooseDayOfMonth();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        setItemsYear(getYear(yearDiff, currentYear));
        setItemsMonth(getMonth(currentMonth));
        setItemsDay(getDay(currentDay, currentDayOfMonth));
        setItemsHour(getHour(currentHour));
        setItemsMinute(getMinute(currentMinute));
        setItemsSecond(getSecond(currentSecond));
        if (StringUtils.isNotBlank(chooseDate))
            yearWheelViewListener.onSelected(chooseYearIndex, chooseYear == 0 ? String.valueOf(currentYear) : String.valueOf(chooseYear));
    }

    private void initView() {
        titleTv = content.findViewById(R.id.textView2);
        content.findViewById(R.id.textView1).setOnClickListener(v -> windowUtil.dismiss());
        confirm = content.findViewById(R.id.textView3);
        yearWl = content.findViewById(R.id.wheelView1);
        monthWl = content.findViewById(R.id.wheelView2);
        dayWl = content.findViewById(R.id.wheelView3);
        hourWl = content.findViewById(R.id.wheelView4);
        minuteWl = content.findViewById(R.id.wheelView5);
        secondWl = content.findViewById(R.id.wheelView6);
        yearWl.setOffset(1);
        monthWl.setOffset(1);
        dayWl.setOffset(1);
        hourWl.setOffset(1);
        minuteWl.setOffset(1);
        secondWl.setOffset(1);

        if (beforeOfAfter == null) {
            yearWheelViewListener = new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseYear = Integer.valueOf(getSelectedItemYear());
                    chooseYearIndex = selectedIndex;
                    currentDayOfMonth = getChooseDayOfMonth();

                            setItemsDay(getDay(0, currentDayOfMonth));
                            chooseDay = Integer.valueOf(getSelectedItemDay());
                }
            };
            yearWl.setOnWheelViewListener(yearWheelViewListener);

            monthWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseMonth = Integer.valueOf(getSelectedItemMonth());
                    chooseMonthIndex = selectedIndex;
                    currentDayOfMonth = getChooseDayOfMonth();

                    setItemsDay(getDay(0, currentDayOfMonth));
                }

            });
            dayWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseDay = Integer.valueOf(getSelectedItemDay());
                    chooseDayIndex = selectedIndex - 1;
                }
            });
        } else {
            yearWheelViewListener = new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseYear = Integer.valueOf(getSelectedItemYear());
                    chooseYearIndex = selectedIndex;
                    currentDayOfMonth = getChooseDayOfMonth();
                    boolean hasBegin = hasBeginYear();

                    if (hasBegin) {
                        setItemsMonth(getMonth(currentMonth));
                        chooseMonth = Integer.valueOf(getSelectedItemMonth());
                        if (hasBeginMonth()) {
                            setItemsDay(getDay(currentDay, currentDayOfMonth));
                            chooseDay = Integer.valueOf(getSelectedItemDay());
                        }
                        if (hasBeginDay()) {
                            setItemsHour(getHour(currentHour));
                            chooseHour = Integer.valueOf(getSelectedItemHour());
                        }
                        if (hasBeginHour()) {
                            setItemsMinute(getMinute(currentMinute));
                            chooseMinute = Integer.valueOf(getSelectedItemMinute());
                        }
                        if (hasBeginMinute()) {
                            setItemsSecond(getSecond(currentSecond));
                            chooseSecond = Integer.valueOf(getSelectedItemSecond());
                        }
                    } else {
                        if (monthWl.getItems().size() != 14)//首尾为空
                            setItemsMonth(getMonth(beforeOfAfter ? 12 : 1));
                        if (dayWl.getItems().size() - 2 != currentDayOfMonth) {//首尾为空
                            setItemsDay(getDay(beforeOfAfter ? currentDayOfMonth : 1, currentDayOfMonth));
                        }
                        if (hourWl.getItems().size() != 26)//首尾为空
                            setItemsHour(getHour(beforeOfAfter ? 24 : 0));
                        if (minuteWl.getItems().size() != 62)//首尾为空
                            setItemsMinute(getMinute(beforeOfAfter ? 60 : 0));
                        if (secondWl.getItems().size() != 62)//首尾为空
                            setItemsSecond(getSecond(beforeOfAfter ? 60 : 0));
                    }
                }
            };
            yearWl.setOnWheelViewListener(yearWheelViewListener);

            monthWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseMonth = Integer.valueOf(getSelectedItemMonth());
                    chooseMonthIndex = selectedIndex;
                    currentDayOfMonth = getChooseDayOfMonth();
                    boolean hasBegin = hasBeginMonth();

                    if (hasBegin) {
                        setItemsDay(getDay(currentDay, currentDayOfMonth));
                        chooseDay = Integer.valueOf(getSelectedItemDay());

                        if (hasBeginDay()) {
                            setItemsHour(getHour(currentHour));
                            chooseHour = Integer.valueOf(getSelectedItemHour());
                        }
                        if (hasBeginHour()) {
                            setItemsMinute(getMinute(currentMinute));
                            chooseMinute = Integer.valueOf(getSelectedItemMinute());
                        }
                        if (hasBeginMinute()) {
                            setItemsSecond(getSecond(currentSecond));
                            chooseSecond = Integer.valueOf(getSelectedItemSecond());
                        }
                    } else {
                        if (dayWl.getItems().size() - 2 != currentDayOfMonth) {//首尾为空
                            setItemsDay(getDay(beforeOfAfter ? currentDayOfMonth : 1, currentDayOfMonth));
                        }
                        if (hourWl.getItems().size() != 26)//首尾为空
                            setItemsHour(getHour(beforeOfAfter ? 24 : 0));
                        if (minuteWl.getItems().size() != 62)//首尾为空
                            setItemsMinute(getMinute(beforeOfAfter ? 60 : 0));
                        if (secondWl.getItems().size() != 62)//首尾为空
                            setItemsSecond(getSecond(beforeOfAfter ? 60 : 0));
                    }
                }

            });

            dayWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseDay = Integer.valueOf(getSelectedItemDay());
                    chooseDayIndex = selectedIndex - 1;
                    boolean hasBegin = hasBeginDay();

                    if (hasBegin) {
                        setItemsHour(getHour(currentHour));
                        chooseHour = Integer.valueOf(getSelectedItemHour());
                        if (hasBeginHour()) {
                            setItemsMinute(getMinute(currentMinute));
                            chooseMinute = Integer.valueOf(getSelectedItemMinute());
                        }
                        if (hasBeginMinute()) {
                            setItemsSecond(getSecond(currentSecond));
                            chooseSecond = Integer.valueOf(getSelectedItemSecond());
                        }
                    } else {
                        if (hourWl.getItems().size() != 26)//首尾为空
                            setItemsHour(getHour(beforeOfAfter ? 24 : 0));
                        if (minuteWl.getItems().size() != 62)//首尾为空
                            setItemsMinute(getMinute(beforeOfAfter ? 60 : 0));
                        if (secondWl.getItems().size() != 62)//首尾为空
                            setItemsSecond(getSecond(beforeOfAfter ? 60 : 0));
                    }
                }
            });

            hourWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseHour = Integer.valueOf(getSelectedItemHour());
                    chooseHourIndex = selectedIndex;
                    boolean hasBegin = hasBeginHour();

                    if (hasBegin) {
                        setItemsMinute(getMinute(currentMinute));
                        chooseMinute = Integer.valueOf(getSelectedItemMinute());

                        if (hasBeginMinute()) {
                            setItemsSecond(getSecond(currentSecond));
                            chooseSecond = Integer.valueOf(getSelectedItemSecond());
                        }
                    } else {
                        if (minuteWl.getItems().size() != 62)//首尾为空
                            setItemsMinute(getMinute(beforeOfAfter ? 60 : 0));
                        if (secondWl.getItems().size() != 62)//首尾为空
                            setItemsSecond(getSecond(beforeOfAfter ? 60 : 0));
                    }
                }
            });

            minuteWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseMinute = Integer.valueOf(getSelectedItemMinute());
                    chooseMinuteIndex = selectedIndex;
                    boolean hasBegin = hasBeginMinute();

                    if (hasBegin) {
                        setItemsSecond(getSecond(currentSecond));
                        chooseSecond = Integer.valueOf(getSelectedItemSecond());
                    } else {
                        if (secondWl.getItems().size() != 62) {//首尾为空
                            setItemsSecond(getSecond(beforeOfAfter ? 60 : 0));
                        }
                    }
                }
            });

            secondWl.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    super.onSelected(selectedIndex, item);
                    chooseSecond = Integer.valueOf(getSelectedItemSecond());
                    chooseSecondIndex = selectedIndex - 1;
                }
            });
        }
    }

    private List<String> getYear(int yearDiff, int year) {
        chooseYearIndex = null;
        List<String> list = new ArrayList<>();

        if (beforeOfAfter == null) {//全部
            for (int i = yearDiff; i > 0; i--) {
                list.add(String.format("%s年", String.valueOf(year - i)));
                if (chooseYear != 0 && chooseYear == year - i)
                    chooseYearIndex = list.size() - 1;
            }
            int index = list.size();
            for (int i = 0; i <= yearDiff; i++) {
                list.add(String.format("%s年", String.valueOf(year + i)));
                if (chooseYear != 0 && chooseYear == year + i)
                    chooseYearIndex = list.size() - 1;
            }
            if (chooseYearIndex == null)
                chooseYearIndex = index;
        } else if (beforeOfAfter) {
            for (int i = yearDiff; i >= 0; i--) {//之前
                list.add(String.format("%s年", String.valueOf(year - i)));
                if (chooseYear != 0 && chooseYear == year - i)
                    chooseYearIndex = list.size() - 1;
            }
            if (chooseYearIndex == null)
                chooseYearIndex = list.size() - 1;
        } else {
            for (int i = 0; i <= yearDiff; i++) {// 之后
                list.add(String.format("%s年", String.valueOf(year + i)));
                if (chooseYear != 0 && chooseYear == year + i)
                    chooseYearIndex = list.size() - 1;
            }
            if (chooseYearIndex == null)
                chooseYearIndex = 0;
        }
        return list;
    }

    private List<String> getMonth(int monthDiff) {
        chooseMonthIndex = null;
        List<String> list = new ArrayList<>();

        if (beforeOfAfter == null) {
            for (int i = 1; i <= 12; i++) {
                list.add(String.format("%02d月", i));
                if (chooseMonth != 0 && chooseMonth == i)
                    chooseMonthIndex = list.size() - 1;
            }
            if (chooseMonthIndex == null)
                chooseMonthIndex = currentMonth - 1;
        } else if (beforeOfAfter) {
            for (int i = 1; i <= monthDiff; i++) {
                list.add(String.format("%02d月", i));
                if (chooseMonth != 0 && chooseMonth == i)
                    chooseMonthIndex = list.size() - 1;
            }
            if (chooseMonthIndex == null)
                chooseMonthIndex = list.size() - 1;
        } else {
            for (int i = monthDiff; i <= 12; i++) {
                list.add(String.format("%02d月", i));
                if (chooseMonth != 0 && chooseMonth == i)
                    chooseMonthIndex = list.size() - 1;
            }
            if (chooseMonthIndex == null)
                chooseMonthIndex = 0;
        }
        return list;
    }

    private List<String> getDay(int dayDiff, int countDay) {
        chooseDayIndex = null;
        List<String> list = new ArrayList<>();

        if (beforeOfAfter == null) {
            for (int i = 1; i <= countDay; i++) {
                list.add(String.format("%02d日", i));
                if (chooseDay != 0 && chooseDay == i)
                    chooseDayIndex = list.size() - 1;
            }
            if (chooseDayIndex == null)
                chooseDayIndex = currentDay - 1;
        } else if (beforeOfAfter) {
            for (int i = 1; i <= dayDiff; i++) {
                list.add(String.format("%02d日", i));
                if (chooseDay != 0 && chooseDay == i)
                    chooseDayIndex = list.size() - 1;
            }
            if (chooseDayIndex == null)
                chooseDayIndex = list.size() - 1;
        } else {
            for (int i = dayDiff; i <= countDay; i++) {
                list.add(String.format("%02d日", i));
                if (chooseDay != 0 && chooseDay == i)
                    chooseDayIndex = list.size() - 1;
            }
            if (chooseDayIndex == null)
                chooseDayIndex = 0;
        }
        return list;
    }

    private List<String> getHour(int hourDiff) {
        chooseHourIndex = null;
        List<String> list = new ArrayList<>();

        if (beforeOfAfter == null) {
            for (int i = 0; i <= 23; i++) {
                list.add(String.format("%02d时", i));
                if (chooseHour != 0 && chooseHour == i)
                    chooseHourIndex = list.size() - 1;
            }
            if (chooseHourIndex == null)
                chooseHourIndex = currentHour;
        } else if (beforeOfAfter) {
            int hourEnd = hourDiff == 24 ? 23 : hourDiff;
            for (int i = 0; i <= hourEnd; i++) {
                list.add(String.format("%02d时", i));
                if (chooseHour != 0 && chooseHour == i)
                    chooseHourIndex = list.size() - 1;
            }
            if (chooseHourIndex == null)
                chooseHourIndex = list.size() - 1;
        } else {
            int hourEnd = hourDiff == 24 ? 0 : hourDiff;
            for (int i = hourEnd; i < 24; i++) {
                list.add(String.format("%02d时", i));
                if (chooseHour != 0 && chooseHour == i)
                    chooseHourIndex = list.size() - 1;

            }
            if (chooseHourIndex == null)
                chooseHourIndex = 0;
        }
        return list;
    }

    private List<String> getMinute(int minuteDiff) {
        chooseMinuteIndex = null;
        List<String> list = new ArrayList<>();

        if (beforeOfAfter == null) {
            for (int i = 0; i <= 59; i++) {
                list.add(String.format("%02d分", i));
                if (chooseMinute != 0 && chooseMinute == i)
                    chooseMinuteIndex = list.size() - 1;
            }
            if (chooseMinuteIndex == null)
                chooseMinuteIndex = currentMinute;
        } else if (beforeOfAfter) {
            int minuteEnd = minuteDiff == 60 ? 59 : minuteDiff;
            for (int i = 0; i <= minuteEnd; i++) {
                list.add(String.format("%02d分", i));
                if (chooseMinute != 0 && chooseMinute == i)
                    chooseMinuteIndex = list.size() - 1;
            }
            if (chooseMinuteIndex == null)
                chooseMinuteIndex = list.size() - 1;
        } else {
            int minuteEnd = minuteDiff == 60 ? 0 : minuteDiff;
            for (int i = minuteEnd; i < 60; i++) {
                list.add(String.format("%02d分", i));
                if (chooseMinute != 0 && chooseMinute == i)
                    chooseMinuteIndex = list.size() - 1;
            }
            if (chooseMinuteIndex == null)
                chooseMinuteIndex = 0;
        }
        return list;
    }

    private List<String> getSecond(int secondDiff) {
        chooseSecondIndex = null;
        List<String> list = new ArrayList<>();

        if (beforeOfAfter == null) {
            for (int i = 0; i <= 59; i++) {
                list.add(String.format("%02d秒", i));
                if (chooseSecond != 0 && chooseSecond == i)
                    chooseSecondIndex = list.size() - 1;
            }
            if (chooseSecondIndex == null)
                chooseSecondIndex = currentSecond;
        } else if (beforeOfAfter) {
            int secondEnd = secondDiff == 60 ? 59 : secondDiff;
            for (int i = 0; i <= secondEnd; i++) {
                list.add(String.format("%02d秒", i));
                if (chooseSecond != 0 && chooseSecond == i)
                    chooseSecondIndex = list.size() - 1;
            }
            if (chooseSecondIndex == null)
                chooseSecondIndex = list.size() - 1;
        } else {
            int secondEnd = secondDiff == 60 ? 0 : secondDiff;
            for (int i = secondEnd; i < 60; i++) {
                list.add(String.format("%02d秒", i));
                if (chooseSecond != 0 && chooseSecond == i)
                    chooseSecondIndex = list.size() - 1;
            }
            if (chooseSecondIndex == null)
                chooseSecondIndex = 0;
        }
        return list;
    }

    public DatePopupWindow setItemsYear(List<String> items) {
        yearWl.setItems(new ArrayList<>(items));
        yearWl.setSeletion(chooseYearIndex);
        return this;
    }

    public DatePopupWindow setItemsMonth(List<String> items) {
        monthWl.setItems(new ArrayList<>(items));
        monthWl.setSeletion(chooseMonthIndex);
        return this;
    }

    public DatePopupWindow setItemsDay(List<String> items) {
        dayWl.setItems(new ArrayList<>(items));
        dayWl.setSeletion(chooseDayIndex);
        return this;
    }

    public DatePopupWindow setItemsHour(List<String> items) {
        hourWl.setItems(new ArrayList<>(items));
        hourWl.setSeletion(chooseHourIndex);
        return this;
    }

    public DatePopupWindow setItemsMinute(List<String> items) {
        minuteWl.setItems(new ArrayList<>(items));
        minuteWl.setSeletion(chooseMinuteIndex);
        return this;
    }

    public DatePopupWindow setItemsSecond(List<String> items) {
        secondWl.setItems(new ArrayList<>(items));
        secondWl.setSeletion(chooseSecondIndex);
        return this;
    }

    public String getSelectedItemYear() {
        return yearWl.getSeletedItem().replace("年", "");
    }

    public String getSelectedItemMonth() {
        return String.format("%02d", Integer.valueOf(monthWl.getSeletedItem().replace("月", "")));
    }

    public String getSelectedItemDay() {
        return String.format("%02d", Integer.valueOf(dayWl.getSeletedItem().replace("日", "")));
    }

    public String getSelectedItemHour() {
        return String.format("%02d", Integer.valueOf(hourWl.getSeletedItem().replace("时", "")));
    }

    public String getSelectedItemMinute() {
        return String.format("%02d", Integer.valueOf(minuteWl.getSeletedItem().replace("分", "")));
    }

    public String getSelectedItemSecond() {
        return String.format("%02d", Integer.valueOf(secondWl.getSeletedItem().replace("秒", "")));
    }

    private int getCurrentDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 0);//当前日期31bug
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private int getChooseDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, chooseYear);
        calendar.set(Calendar.MONTH, chooseMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 0);//当前日期31bug
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private boolean hasBeginYear() {
        return (currentYear == chooseYear);
    }

    private boolean hasBeginMonth() {
        return (currentYear == chooseYear && currentMonth == chooseMonth);
    }

    private boolean hasBeginDay() {
        return (currentYear == chooseYear && currentMonth == chooseMonth && currentDay == chooseDay);
    }

    private boolean hasBeginHour() {
        return (currentYear == chooseYear && currentMonth == chooseMonth && currentDay == chooseDay
                && currentHour == chooseHour);
    }

    private boolean hasBeginMinute() {
        return (currentYear == chooseYear && currentMonth == chooseMonth && currentDay == chooseDay
                && currentHour == chooseHour && currentMinute == chooseMinute);
    }

    public PopupWindow show(DatePopupListener listener) {
        confirm.setOnClickListener(v -> {
            listener.item(getSelectedItemYear(), getSelectedItemMonth(), getSelectedItemDay(), getSelectedItemHour(), getSelectedItemMinute(), getSelectedItemSecond());
            windowUtil.dismiss();
        });
        return show(Gravity.BOTTOM, 0, 0);
    }

    public interface DatePopupListener {
        void item(String year, String month, String day, String hour, String minute, String second);
    }

    public DatePopupWindow showYear(boolean visible) {
        if (visible)
            this.yearWl.setVisibility(View.VISIBLE);
        else
            this.yearWl.setVisibility(View.GONE);
        return this;
    }

    public DatePopupWindow showMonth(boolean visible) {
        if (visible)
            this.monthWl.setVisibility(View.VISIBLE);
        else
            this.monthWl.setVisibility(View.GONE);
        return this;
    }

    public DatePopupWindow showDay(boolean visible) {
        if (visible)
            this.dayWl.setVisibility(View.VISIBLE);
        else
            this.dayWl.setVisibility(View.GONE);
        return this;
    }

    public DatePopupWindow showHour(boolean visible) {
        if (visible)
            this.hourWl.setVisibility(View.VISIBLE);
        else
            this.hourWl.setVisibility(View.GONE);
        return this;
    }

    public DatePopupWindow showMinute(boolean visible) {
        if (visible)
            this.minuteWl.setVisibility(View.VISIBLE);
        else
            this.minuteWl.setVisibility(View.GONE);
        return this;
    }

    public DatePopupWindow showSecond(boolean visible) {
        if (visible)
            this.secondWl.setVisibility(View.VISIBLE);
        else
            this.secondWl.setVisibility(View.GONE);
        return this;
    }

    public DatePopupWindow setTitle(String title) {
        titleTv.setText(title);
        return this;
    }

    public WheelView getYearWl() {
        return yearWl;
    }

    public WheelView getMonthWl() {
        return monthWl;
    }

    public WheelView getDayWl() {
        return dayWl;
    }

    public WheelView getHourWl() {
        return hourWl;
    }

    public WheelView getMinuteWl() {
        return minuteWl;
    }

    public WheelView getSecondWl() {
        return secondWl;
    }

    public void setDateFormat(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
}
