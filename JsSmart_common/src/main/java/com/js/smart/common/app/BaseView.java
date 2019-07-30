package com.js.smart.common.app;


public interface BaseView {

    void showLoading();

    void hideLoading();

    void httpError(String message, int type, Object data);

}
