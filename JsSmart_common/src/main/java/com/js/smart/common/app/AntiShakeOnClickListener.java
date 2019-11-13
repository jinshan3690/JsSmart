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

    public static void resetOnClick(){//解决butterknife fragment销毁时状态不能重置
        try {
            Field field = DebouncingOnClickListener.class.getDeclaredField("enabled");
            field.setAccessible(true);
            field.setBoolean(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
