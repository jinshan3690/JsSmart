package com.js.smart.http;


import com.js.smart.common.app.CommonApp;
import com.js.smart.common.init.strategy.InitStrategy;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Js on 2017/11/28.
 */

public abstract class HttpApp extends CommonApp {

    public static InitStrategy initStrategy;

    public static OkHttpClient client;
    public static Gson gson;
    public static Retrofit retrofit;
    public static Retrofit retrofitSDK;

    public static OkHttpClient getOkHttpClient(){
        return client;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
