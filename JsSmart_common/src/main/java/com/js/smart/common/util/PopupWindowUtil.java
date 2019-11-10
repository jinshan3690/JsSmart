package com.js.smart.common.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.PopupWindow;


public class PopupWindowUtil {

    private Context context;
    private PopupWindow window;
    private View parent;
    private View view;

    public PopupWindowUtil(Context context) {
        this.context = context;
    }

    private void init() {
        setCancel(true);
    }

    public PopupWindow getWindow() {
        return window;
    }

    public View getParent() {
        return parent;
    }

    public View getView() {
        return view;
    }

    /**
     * popup自定义
     */
    public PopupWindowUtil initPopupWindow(View view, View parent, int width, int height) {
        this.parent = parent;
        this.view = view;
        window = new PopupWindow(view, width, height, true);

        init();
        return this;
    }

    /**
     * 外部是否可以点击 点击外部取消
     */
    public PopupWindowUtil setCancel(boolean cancel) {
        window.setOutsideTouchable(cancel);
        window.setFocusable(cancel);
        if (!cancel) {
            window.setBackgroundDrawable(new BitmapDrawable());
        }else {
            window.setBackgroundDrawable(null);
        }
        return this;
    }

    /**
     * 显示的位置
     */
    public PopupWindow showAtLocation(int gravity, int x, int y) {
        window.showAtLocation(parent, gravity, x, y);
        return window;
    }

    public PopupWindow showAsDropDown(int x, int y) {
        window.showAsDropDown(parent, x, y);
        return window;
    }

    /**
     * 背景变暗
     */
    public PopupWindowUtil setObscure() {
        final AppCompatActivity activity = (AppCompatActivity) context;
        SystemUtil.darkenBackground(activity,0.7f);
        window.setOnDismissListener(() -> {
            SystemUtil.darkenBackground(activity, 1f);
        });
        return this;
    }

        public PopupWindowUtil setAnimationStyle(int anim) {
        window.setAnimationStyle(anim);
        return this;
    }

    public void dismiss() {
        window.dismiss();
    }
}
