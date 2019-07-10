package com.js.smart.common.ui.popup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.js.smart.common.R;
import com.js.smart.common.util.PopupWindowUtil;

public class BasePopupWindow<T> {

    protected PopupWindowUtil windowUtil;
    protected View content;
    protected Context context;

    public BasePopupWindow(Context context, View view, @LayoutRes int layoutId) {
        this.context = context;
        content = LayoutInflater.from(context).inflate(layoutId, null);

        windowUtil = new PopupWindowUtil(context).
                initPopupWindow(content, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.popupWindow_anim_style);
    }

    public T setTitle(String title){
        return (T) this;
    }

    public PopupWindow show(int gravity, int x, int y){
        return windowUtil.setObscure().showAtLocation(gravity, x, y);
    }

}
