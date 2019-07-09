package com.js.smart.ui.app;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.js.smart.ui.UIApp;
import com.js.smart.common.init.strategy.InitTask;
import com.js.smart.common.util.L;
import com.js.smart.http.config.factory.GsonConverterFactory;
import com.google.gson.GsonBuilder;
import com.js.smart.http.cookies.CookiesManager;
import com.js.smart.http.gson.StringTypeAdapter;
import com.js.smart.http.interceptor.HeaderInterceptor;
import com.js.smart.http.interceptor.ParameterInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class UIInitTask implements InitTask {

    @Override
    public void init(Context context, Retrofit retrofit) {

        LeakCanary.install((Application) context.getApplicationContext());

        AutoSizeConfig.getInstance().setCustomFragment(true);

        initX5WebView(context);

        initHttp(context);

    }

    private void initHttp(Context context) {
        RetrofitUrlManager.getInstance().setGlobalDomain(UIApp.BaseUrl);
//        RetrofitUrlManager.getInstance().putDomain("douban", "https://api.douban.com");

        UIApp.gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new StringTypeAdapter())
                .create();

        File httpCacheDirectory = new File(context.getCacheDir(), "httpCache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> L.e(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        UIApp.client = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .cookieJar(CookiesManager.getInstance(context))
                .cache(cache)
                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new NetworkCacheInterceptor())//only get
                .addInterceptor(new ParameterInterceptor())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        UIApp.retrofit = new Retrofit.Builder().
                baseUrl(UIApp.BaseUrl).
                addConverterFactory(GsonConverterFactory.create(UIApp.gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                client(UIApp.client).
                build();

        UIApp.retrofitSDK = new Retrofit.Builder().
                baseUrl(UIApp.BaseUrl).
                addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(new Gson())).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                client(UIApp.client).
                build();
    }

    protected void initX5WebView(Context context) {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                System.out.println(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(context, cb);
    }

}
