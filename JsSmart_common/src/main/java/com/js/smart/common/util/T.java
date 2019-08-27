package com.js.smart.common.util;

import android.widget.Toast;

import com.js.smart.common.app.CommonApp;

import es.dmoral.toasty.Toasty;

//Toast统一管理类
public class T {

    public static int duration = Toast.LENGTH_SHORT;

    public static void showError(CharSequence message) {
        Toasty.Config.reset();
        Toasty.error(CommonApp.getContext(), message, duration, true).show();
    }

    public static void showSuccess(CharSequence message) {
        Toasty.Config.reset();
        Toasty.success(CommonApp.getContext(), message, duration, true).show();
    }

    public static void showInfo(CharSequence message) {
        Toasty.Config.reset();
        Toasty.info(CommonApp.getContext(), message, duration, true).show();
    }

    public static void showWarning(CharSequence message) {
        Toasty.Config.reset();
        Toasty.warning(CommonApp.getContext(), message, duration, true).show();
    }

    public static void showNormal(CharSequence message) {
        Toasty.Config.reset();
        Toasty.normal(CommonApp.getContext(), message, duration).show();
    }

}