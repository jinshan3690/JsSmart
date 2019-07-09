package com.js.smart.http.result;




import android.content.Context;


import com.google.gson.Gson;
import com.js.smart.http.R;
import com.js.smart.http.bean.BaseResp;
import com.js.smart.http.exception.HttpException;
import com.js.smart.common.util.L;
import com.js.smart.common.util.ReceiverManager;
import com.js.smart.common.util.T;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by Js on 2016/5/24.
 * DisposableObserver
 */
public abstract class HttpResult<Y> extends DisposableObserver<Y> {

    private Context context;

    protected HttpResult(Context context) {
        this.context = context;
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

        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            message = context.getResources().getString(R.string.network_error);

            code = -98;
            T.showWarning(message);
        }else if(e instanceof retrofit2.HttpException){
            retrofit2.HttpException httpException1 = (retrofit2.HttpException) e;
            code = httpException1.code();
            if(code == 500){
                message = "服务器异常";
                code = -97;
                T.showError(message);
            }else if (code == 401) {
                L.e("appHttp", context.getResources().getString(R.string.request_failed)+ message);
                ReceiverManager.sendBroadcast(context, ReceiverManager.Action.ACTION_Logout);
                return;
            }else{
                try {
                    message = httpException1.response().errorBody().string();

                    BaseResp<String> respMsg = new Gson().fromJson(message, BaseResp.class);
                    T.showError(respMsg.getMessage());
                } catch (Exception e1) {
                    L.e("appHttp", context.getResources().getString(R.string.request_failed)+ message);
                    e1.printStackTrace();
                }
            }

            e.printStackTrace();
        } else {
            message = e.getMessage();
            e.printStackTrace();
            code = -96;
        }
        L.e("appHttp", context.getResources().getString(R.string.request_failed)+ message);
        onError(code ,message);
    }

    @Override
    public void onNext(Y t) {
        onResult(t);
    }

    protected abstract void onResult(Y result);

    protected abstract void onError(int code, String msg);
}
