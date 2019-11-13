package com.js.smart.common.app;

import android.view.View;

import com.js.smart.common.util.AntiShakeUtils;

import java.lang.reflect.Field;

import butterknife.internal.DebouncingOnClickListener;

public abstract class AntiShakeOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (AntiShakeUtils.isInvalidClick(v))
            return;

        antiShakeOnClick(v);
    }

    protected abstract void antiShakeOnClick(View v);

    public static void resetOnClick(View v){
        try {
            Field field1 = View.class.getDeclaredField("mListenerInfo");
            field1.setAccessible(true);
            Field field2 = field1.get(v).getClass().getDeclaredField("mOnClickListener");
            field2.setAccessible(true);
            View.OnClickListener onClickListener = (View.OnClickListener) field2.get(field1.get(v));
            if(onClickListener instanceof DebouncingOnClickListener) {
                Field field = DebouncingOnClickListener.class.getDeclaredField("enabled");
                field.setAccessible(true);
                field.setBoolean(onClickListener, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
