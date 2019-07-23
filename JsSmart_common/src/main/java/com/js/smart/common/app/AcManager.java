package com.js.smart.common.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.js.smart.common.R;
import com.js.smart.common.app.ac_anim.AcAnimInterface;
import com.js.smart.common.util.AntiShakeUtils;
import com.js.smart.common.util.T;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * Created by Js on 2016/6/29.
 */
public class AcManager {

    private BaseCompatActivity context;

    private boolean isDoubleExit;
    private boolean isBackKey = true;
    private int color = R.color.statusBarColor;
    private boolean statusBarVisibility = true;
    private boolean statusTrans = false;
    private SystemBarTintManager mTintManager;

    public AcManager(BaseCompatActivity context) {
        this.context = context;

//        view = context.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public static AcManager getInstance(BaseCompatActivity context) {
        return new AcManager(context);
    }

    /**
     * 改变状态栏颜色
     */
    public void changeStatusColor(View v, int color) {
        setStateBarColor(color);
        changeStatusBar(v);
    }

    /**
     * 改变状态栏颜色
     */
    public void changeStatusBar(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mTintManager == null) {
                setTranslucentStatus(v);
            } else {
                mTintManager.setStatusBarTintResource(color);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(View v) {

        Window window = context.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        int stateHeight = getStatusHeight();

        if (!statusTrans) {
            mTintManager = new SystemBarTintManager(context);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(color);
            int actionHeight = getActionHeight();


            if (!statusBarVisibility)
                stateHeight = 0;
            v.setPadding(0, stateHeight + actionHeight, 0, 0);
        } else {
            v.setPadding(0, 0, 0, 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setStateTextDark() {
        Window window = context.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight() {
        int statusHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得ActionBar的高度
     */
    public int getActionHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        if (context instanceof AppCompatActivity) {
            AppCompatActivity compatActivity = context;
            if (compatActivity.getSupportActionBar() == null || !compatActivity.getSupportActionBar().isShowing())
                actionBarHeight = 0;
        } else {
            actionBarHeight = 0;
        }
        return actionBarHeight;
    }

    /**
     * 显示隐藏状态栏
     */
    public void setStatusBarVisibility(BaseCompatActivity context, boolean enable) {
        this.statusBarVisibility = enable;
        Window window = context.getWindow();

        if (!enable) {
            mTintManager.setStatusBarTintResource(R.color.colorTransparent);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            mTintManager = new SystemBarTintManager(context);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(R.color.colorTransparent);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }

            mTintManager = new SystemBarTintManager(context);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(color);
        }

        int actionHeight = getActionHeight();
        int stateHeight = getStatusHeight();
        if (!enable)
            stateHeight = 0;
        context.view.setPadding(0, stateHeight + actionHeight, 0, 0);
    }

    public void setStatusLight(Activity context) {
        Window window = context.getWindow();
        mTintManager.setStatusBarTintResource(R.color.colorTransparent);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    public void setStatusDark(Activity context) {
        Window window = context.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 设置是否全屏
     */
    public void setFullScreen(boolean enable) {
        Window window = context.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (enable) {
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        window.setAttributes(lp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 设置是否横屏
     */
    public void setOrientation(boolean enable) {
        if (enable)
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * initView使用状态栏颜色
     */
    public void setStateBarColor(int color) {
        this.color = color;
    }

    public void setStatusTrans(boolean statusTrans) {
        this.statusTrans = statusTrans;
    }

    /**
     * 跳转方法
     */
    public static void toActivity(Context context, String url) {
        ARouter.getInstance().build(url).navigation(context);
    }

    public static void toActivity(Context context, String url, AcAnimInterface animInterface) {
        ARouter.getInstance().build(url).withTransition(animInterface.enterAnim(), animInterface.exitAnim()).navigation(context);
    }

    public static void toActivity(Context context, String url, ActivityOptionsCompat compat) {
        ARouter.getInstance()
                .build(url)
                .withOptionsCompat(compat).navigation(context);
    }

    public static void toActivityForResult(Activity context, String url, int request) {
        ARouter.getInstance().build(url).navigation(context, request);
    }

    public static void toActivityForResult(Activity context, String url, AcAnimInterface animInterface, int request) {
        ARouter.getInstance().build(url).withTransition(animInterface.enterAnim(), animInterface.exitAnim()).navigation(context, request);
    }

    public static void toActivityForResult(Activity context, String url, ActivityOptionsCompat compat, int request) {
        ARouter.getInstance()
                .build(url)
                .withOptionsCompat(compat).navigation(context, request);
    }

    public static void toActivity(String url, LoadDataDoneCallback callback) {
        Postcard postcard = ARouter.getInstance().build(url);
        if (callback != null)
            callback.done(postcard);
    }

    public static void toActivity(String url, AcAnimInterface animInterface, LoadDataDoneCallback callback) {
        Postcard postcard = ARouter.getInstance().build(url).withTransition(animInterface.enterAnim(), animInterface.exitAnim());
        if (callback != null)
            callback.done(postcard);
    }

    public static void toActivity(String url, ActivityOptionsCompat compat, LoadDataDoneCallback callback) {
        Postcard postcard = ARouter.getInstance()
                .build(url)
                .withOptionsCompat(compat);
        if (callback != null)
            callback.done(postcard);
    }

    public static ActivityOptionsCompat getCompat(Activity context, View v, String target) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, v, target);
        return options;
    }

    /**
     * 防抖
     */
    public static void toActivity(Context context, View v, String url) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        ARouter.getInstance().build(url).navigation(context);
    }

    public static void toActivity(Context context, View v, String url, AcAnimInterface animInterface) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        ARouter.getInstance().build(url).withTransition(animInterface.enterAnim(), animInterface.exitAnim()).navigation(context);
    }

    public static void toActivity(Context context, View v, String url, ActivityOptionsCompat compat) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        ARouter.getInstance()
                .build(url)
                .withOptionsCompat(compat).navigation(context);
    }

    public static void toActivityForResult(Activity context, View v, String url, int request) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        ARouter.getInstance().build(url).navigation(context, request);
    }

    public static void toActivityForResult(Activity context, View v, String url, AcAnimInterface animInterface, int request) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        ARouter.getInstance().build(url).withTransition(animInterface.enterAnim(), animInterface.exitAnim()).navigation(context, request);
    }

    public static void toActivityForResult(Activity context, View v, String url, ActivityOptionsCompat compat, int request) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        ARouter.getInstance()
                .build(url)
                .withOptionsCompat(compat).navigation(context, request);
    }

    public static void toActivity(View v, String url, LoadDataDoneCallback callback) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        Postcard postcard = ARouter.getInstance().build(url);
        if (callback != null)
            callback.done(postcard);
    }

    public static void toActivity(View v, String url, AcAnimInterface animInterface, LoadDataDoneCallback callback) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        Postcard postcard = ARouter.getInstance().build(url).withTransition(animInterface.enterAnim(), animInterface.exitAnim());
        if (callback != null)
            callback.done(postcard);
    }

    public static void toActivity(View v, String url, ActivityOptionsCompat compat, LoadDataDoneCallback callback) {
        if (v != null && AntiShakeUtils.isInvalidClick(v))
            return;
        Postcard postcard = ARouter.getInstance()
                .build(url)
                .withOptionsCompat(compat);
        if (callback != null)
            callback.done(postcard);
    }

    public interface LoadDataDoneCallback {
        void done(Postcard postcard);
    }

    /**
     * 双击退出
     */
    private boolean isExit;

    public boolean doubleExit(int keyCode) {
        if (!isBackKey)
            return true;
        if (isDoubleExit && !isExit && keyCode == KeyEvent.KEYCODE_BACK) {
            T.showInfo(context.getResources().getString(R.string.press_exit_again));
            isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 3000);
            return true;
        }
        return false;
    }

    /**
     * 开始双击退出
     */
    public void setDoubleExit(boolean isDoubleExit) {
        this.isDoubleExit = isDoubleExit;
    }

    /**
     * 屏蔽返回键
     */
    public void setBackKey(boolean isBackKey) {
        this.isBackKey = isBackKey;
    }

    /**
     * 点击屏幕隐藏软键盘
     */
    public boolean hideSoftInput(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction() && null != context.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
        return false;
    }

    public boolean isStatusTrans() {
        return statusTrans;
    }
}
