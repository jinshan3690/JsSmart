package com.js.smart.http.config.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.js.smart.common.util.L;
import com.js.smart.http.cookies.CookiesManager;
import com.js.smart.http.interceptor.HeaderInterceptor;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by Js on 2017/12/6.
 */

@GlideModule
public final class GlideConfig extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //通过builder.setXXX进行配置
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        File httpCacheDirectory = new File(context.getCacheDir(), "httpCache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> L.e(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(CookiesManager.getInstance(context))
                .cache(cache)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }

}