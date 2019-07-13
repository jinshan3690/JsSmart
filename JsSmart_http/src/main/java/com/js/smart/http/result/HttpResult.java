package com.js.smart.http.result;


import android.content.Context;

import com.google.gson.Gson;
import com.js.smart.common.util.L;
import com.js.smart.common.util.T;
import com.js.smart.http.R;
import com.js.smart.http.bean.BaseResp;
import com.js.smart.http.exception.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by Js on 2016/5/24.
 * DisposableObserver
 */
public abstract class HttpResult<Y> extends DisposableObserver<Y> {

    protected Context context;
    protected boolean showToast = true;

    protected HttpResult(Context context) {
        this.context = context;
    }

    protected HttpResult(Context context, boolean showToast) {
        this.context = context;
        this.showToast = showToast;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        String message = e.getMessage();
        int code;
        HttpException httpException;

        if (e instanceof HttpException) {

            httpException = (HttpException) e;
            message = httpException.getMessage();
            code = httpException.getCode();

            if (showToast)
                T.showError(message);
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            message = context.getResources().getString(R.string.network_error);

            code = -9998;
            T.showError(message);
        } else if (e instanceof retrofit2.HttpException) {
            retrofit2.HttpException httpException1 = (retrofit2.HttpException) e;
            code = httpException1.code();
            if (code == 500) {
                message = context.getResources().getString(R.string.server_exception);
                T.showError(message);
            } else {
                try {
                    message = httpException1.response().errorBody().string();

                    BaseResp<String> respMsg = new Gson().fromJson(message, BaseResp.class);
                    if (showToast)
                        T.showError(respMsg.getMessage());
                } catch (Exception e1) {
                    L.e("appHttp", context.getResources().getString(R.string.request_failed) + message);
                    e1.printStackTrace();
                }
            }

            e.printStackTrace();
        } else {
            message = e.getMessage();
            e.printStackTrace();
            code = -9996;
        }
        L.e("appHttp", context.getResources().getString(R.string.request_failed) + message);
        onError(code, message);
    }

    @Override
    public void onNext(Y t) {
        onResult(t);
    }

    protected abstract void onResult(Y result);

    protected abstract void onError(int code, String msg);
}
