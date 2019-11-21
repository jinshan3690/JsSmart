package com.js.smart.common.util;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.js.smart.common.app.CommonApp;

import java.lang.reflect.Field;

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

    public static void showCustom(int layoutId){
        View view = LayoutInflater.from(CommonApp.getContext()).inflate(layoutId, null , false);
        showCustom(view, Gravity.BOTTOM, 0, 0, 0);
    }

    public static void showCustom(View view){
        showCustom(view, Gravity.BOTTOM, 0, 0, 0);
    }

    public static void showCustom(View view, int gravity){
        showCustom(view, gravity, 0, 0, 0);
    }

    public static void showCustom(View view, int gravity, int xOffset, int yOffset){
        showCustom(view, gravity, xOffset, yOffset, 0);
    }

    public static void showCustom(View view, int gravity, int xOffset, int yOffset, int styleAnim){
        final Toast currentToast = Toast.makeText(CommonApp.getContext(), "", duration);
        currentToast.setView(view);
        currentToast.setGravity(gravity, xOffset, yOffset);

        try {
            Object mTN ;
            mTN = getField(currentToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    if (styleAnim != 0)
                        params.windowAnimations = styleAnim;
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentToast.show();
    }

    /**
     * 反射字段
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

}