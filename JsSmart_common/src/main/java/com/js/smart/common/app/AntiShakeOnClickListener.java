package com.js.smart.common.app;

import android.view.View;

import com.js.smart.common.util.AntiShakeUtils;

public abstract class AntiShakeOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (AntiShakeUtils.isInvalidClick(v))
            return;

        antiShakeOnClick(v);
    }

    protected abstract void antiShakeOnClick(View v);

}
