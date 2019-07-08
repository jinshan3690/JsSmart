package com.js.smart.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long YEAR = DAY * 365;
    public static final long MONTH = DAY * 10;//10天

    /**
     * 获取时间格式字符串
     * yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrentDate() {
        long time = System.currentTimeMillis();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    public static String getCurrentDate(String formatStr) {
        long time = System.currentTimeMillis();
        DateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(time));
    }

    /**
     * 日期格式字符串转换成时间戳
     * 字符串日期
     * 如：yyyy-MM-dd HH:mm:ss
     */
    public static Long toTime(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static Long toTime(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String getDate(long time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    public static String getDate(long time, String formatStr) {
        DateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(time));
    }

    public static String getCurrentAM_PM() {
        int am = new GregorianCalendar().get(GregorianCalendar.AM_PM);
        return am == 0 ? "上午 " : "下午 ";
    }

    public static String getDiffDate(long time) {
        String date;
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - time;
        if (diff < MINUTE) {
            date = "刚刚";
        } else if (diff < HOUR) {
            date = diff / MINUTE + "分钟前";
        } else if (diff < DAY) {
            date = diff / HOUR + "小时前";
        } else if (diff < MONTH) {
            date = diff / DAY + "天前";
        } else if (getDate(time, "yyyy").equals(getDate(currentTime, "yyyy"))) {
            date = getDate(time, "MM-dd");
        } else {
            date = getDate(time, "yyyy-MM-dd");
        }
        date = date + getDate(time, " a");
        return date;
    }

    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间  为空(null)则为当前时间
     * @param type  返回值类型   0为多少天，1为多少个月，2为多少年
     */
    public static int diffDate(String date1, String date2, int type) {
        int diffDay = 0;
        String[] u = {"天", "月", "年"};
        String formatStyle = type == 1 ? "yyyy-MM" : "yyyy-MM-dd";
        date2 = date2 == null ? DateUtil.getCurrentDate() : date2;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (!c1.after(c2)) {                     // 直到c1大于c2
            diffDay++;
            if (type == 1) {
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }
        diffDay = diffDay - 1;
        if (type == 2) {
            diffDay = diffDay / 365;
        }
        return diffDay;
    }

    /**
     * 获取当月的第一天
     */
    public static Date getCurrentMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
       return c.getTime();
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


}
