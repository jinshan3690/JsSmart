package com.js.smart.ui.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.js.smart.common.app.BaseView;
import com.js.smart.ui.R;

public class UILoadingActivity extends UIBaseActivity implements BaseView {

    protected ViewGroup loading;
    protected int loadingStateColorDark = R.color.colorStateDark;
    protected int loadingStateColor = R.color.colorPrimary;

    @Override
    public int createView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        loading = view.findViewById(R.id.loadingView2);
    }

    public void setLoadingStateColorDark(int loadingStateColorDark) {
        this.loadingStateColorDark = loadingStateColorDark;
    }

    public void setLoadingStateColor(int loadingStateColor) {
        this.loadingStateColor = loadingStateColor;
    }

    @Override
    public void showLoading() {
        context.acManager.changeStatusColor(view, loadingStateColorDark);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        context.acManager.changeStatusColor(view, loadingStateColor);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void httpError(String message, int type, Object data) {

    }
}
