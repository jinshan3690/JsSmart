package com.js.smart.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.js.smart.common.R;
import com.js.smart.common.app.CommonApp;

import java.util.Locale;

public class LanguageManage {

    //当前语言
    private static Locale systemLocal = Locale.ENGLISH;
    private static final String currentLanguage = "current_language";
    private static final String systemLanguage = "system_language";
    private static String shareName = android.R.class.getPackage().getName() + ".lang";

    public static final int AUTO = 0;
    public static final int CN = 1;
    public static final int EN = 2;

    /**
     * 获取系统的locale
     */
    public static String getSelectLanguage(Context context) {
        switch (getCurrentLanguage(context)) {
            case 0:
                return context.getString(R.string.language_auto);
            case 1:
                return context.getString(R.string.language_cn);
            case 2:
                return context.getString(R.string.language_en);
            default:
                return context.getString(R.string.language_auto);
        }
    }

    /**
     * 获取选择的语言设置
     */
    public static Locale getSetLanguageLocale(Context context) {
        switch (getCurrentLanguage(context)) {
            case 0:
                return getSystemLocal();
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.ENGLISH;
            default:
                return getSystemLocal();
        }
    }

    public static void changeLanguage(int select) {
        saveLanguage(select);
        setApplicationLanguage(CommonApp.context);
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    public static void saveSystemLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        L.d("当前语言：" + locale.getLanguage());
        setSystemLocal(locale);
    }

    public static void onConfigurationChanged(Context context) {
        saveSystemLanguage();
        setLocal(context);
        setApplicationLanguage(context);
    }

    /**
     * 缓存
     */
    private static Locale getSystemLocal() {
        return systemLocal;
    }

    private static void setSystemLocal(Locale local) {
        systemLocal = local;
    }

    private static int getCurrentLanguage(Context context) {
        return LocalManager.getInstance(context).getNewShare(shareName).getInt(currentLanguage, 0);
    }

    private static void saveLanguage(int select) {
        SharedPreferences.Editor edit = LocalManager.getInstance(CommonApp.context).getNewShare(shareName).edit();
        edit.putInt(currentLanguage, select);
        edit.apply();
    }
}
