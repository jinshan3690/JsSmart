package com.js.smart.http.interceptor;



import com.js.smart.http.HttpApp;
import com.js.smart.common.util.SystemUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Js on 2016/5/20.
 */
public class NetworkCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();
        Request request = chain.request();

        if (!SystemUtil.isNetworkAvailable(HttpApp.context)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        return chain.proceed(request);
//        Response originalResponse = chain.proceed(request);
//        if (SystemUtil.isNetworkAvailable(App.context)) {
//            // read from cache
//            int maxAge = 0;
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public ,max-age=" + maxAge)
//                    .build();
//        } else {
//            // tolerate 4-weeks stale
//            int maxStale = 60 * 60 * 24 * 28;
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }

    }
}