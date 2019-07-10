package com.js.smart.common.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.js.smart.common.app.ac_anim.AcAnimInterface;
import com.js.smart.common.util.AcStack;
import com.js.smart.common.util.LanguageManage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseCompatActivity extends RxAppCompatActivity implements
         BaseActivityI {

    public AcManager acManager;

    public View view;
    protected BaseCompatActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ARouter.getInstance().inject(this);
        acManager = AcManager.getInstance(context);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        AcStack.create().addActivity(context);
        setContentView(createView(savedInstanceState));
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0)
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        this.view = view;
        super.setContentView(view);
        ButterKnife.bind(this);
        initViewBefore();
        acManager.changeStatusBar(view);
        initView();
    }

    protected void initViewBefore(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AcStack.create().removeActivity(context);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageManage.setLocal(newBase));
    }

    /**
     * 点击隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        acManager.hideSoftInput(event);

        return super.dispatchTouchEvent(event);
    }

    /**
     * 跳转
     */
    public void toActivity(String url) {
        AcManager.toActivity(context, url);
    }

    public void toActivity(String url, AcAnimInterface animInterface) {
        AcManager.toActivity(context, url, animInterface);
    }

    public void toActivity(String url, ActivityOptionsCompat compat) {
        AcManager.toActivity(context, url, compat);
    }

    public void toActivityForResult(String url, int request) {
        AcManager.toActivityForResult(context, url, request);
    }

    public void toActivityForResult(String url, AcAnimInterface animInterface, int request) {
        AcManager.toActivityForResult(context, url, animInterface, request);
    }

    public void toActivityForResult(String url, ActivityOptionsCompat compat, int request) {
        AcManager.toActivityForResult(context, url, compat, request);
    }

    public void toActivityForData(String url, AcManager.LoadDataDoneCallback callback) {
        AcManager.toActivity(url, callback);
    }

    public void toActivityForData(String url, AcAnimInterface animInterface, AcManager.LoadDataDoneCallback callback) {
        AcManager.toActivity(url, animInterface, callback);
    }

    public void toActivityForData(String url, ActivityOptionsCompat compat, AcManager.LoadDataDoneCallback callback) {
        AcManager.toActivity(url, compat, callback);
    }

    public ActivityOptionsCompat getCompat(View v, String target) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, v, target);
        return options;
    }

    /**
     * 跳转 防抖
     */
    public void toActivity(View v, String url) {
        AcManager.toActivity(context, v, url);
    }

    public void toActivity(View v, String url, AcAnimInterface animInterface) {
        AcManager.toActivity(context, v, url, animInterface);
    }

    public void toActivity(View v, String url, ActivityOptionsCompat compat) {
        AcManager.toActivity(context, v, url, compat);
    }

    public void toActivityForResult(View v, String url, int request) {
        AcManager.toActivityForResult(context, v, url, request);
    }

    public void toActivityForResult(View v, String url, AcAnimInterface animInterface, int request) {
        AcManager.toActivityForResult(context, v, url, animInterface, request);
    }

    public void toActivityForResult(View v, String url, ActivityOptionsCompat compat, int request) {
        AcManager.toActivityForResult(context, v, url, compat, request);
    }

    public void toActivityForData(View v, String url, AcManager.LoadDataDoneCallback callback) {
        AcManager.toActivity(v, url, callback);
    }

    public void toActivityForData(View v, String url, AcAnimInterface animInterface, AcManager.LoadDataDoneCallback callback) {
        AcManager.toActivity(v, url, animInterface, callback);
    }

    public void toActivityForData(View v, String url, ActivityOptionsCompat compat, AcManager.LoadDataDoneCallback callback) {
        AcManager.toActivity(v, url, compat, callback);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            context.acManager.setStatusBarVisibility(context, true);
        }else{
            context.acManager.setStatusBarVisibility(context, false);
        }
    }

    /**
     * rxJava
     * ObservableTransformer
     */
    public <B> ObservableTransformer<B, B> applySchedulers(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 双击退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (acManager.doubleExit(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
