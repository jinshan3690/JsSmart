package com.js.smart.http;


import com.google.gson.Gson;
import com.js.smart.common.app.CommonApp;
import com.js.smart.common.init.strategy.InitStrategy;
import com.js.smart.http.config.factory.GsonResponseBodyConverter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Js on 2017/11/28.
 */

public abstract class HttpApp extends CommonApp {

    public static InitStrategy initStrategy;

    public static String hasCache = "hasCache";
    public static OkHttpClient client;
    public static Gson gson;
    public static GsonResponseBodyConverter.OnGsonResponseListener gsonResponseListener;
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
