package com.js.smart.common.app;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.js.smart.common.app.ac_anim.AcAnimInterface;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Js on 2016/5/16.
 */
public abstract class BaseFragment extends RxFragment implements BaseActivityI {

    protected View view;
    protected BaseCompatActivity context;
    protected int layout;

    private boolean isFirst = true;
    private boolean isShow = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = LayoutInflater.from(context).inflate(createView(savedInstanceState),container,false);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this, view);
        initViewBefore(savedInstanceState);
        initView();

        if (isShow && isFirst)
            setUserVisibleHint(true);

        return view;
    }

    protected void initViewBefore(Bundle savedInstanceState){

    }

    /**
     * setUserVisibleHint -> onCreate -> onCreateView -> setUserVisibleHint
     */
    @Override
    public void setUserVisibleHint(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden && isFirst && view != null){
            onFirstView();
            isFirst = false;
        }
    }

    /**
     * 第一次切换时加载
     */
    protected void onFirstView(){

    }

    public void setShow(boolean show) {
        isShow = show;
    }

    /**
     * rxJava
     * ObservableTransformer
     */
    protected <B> ObservableTransformer<B, B> applySchedulers(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

}
