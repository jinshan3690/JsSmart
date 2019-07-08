package com.js.smart.http.interceptor;



import com.js.smart.common.util.L;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Js on 2016/5/20.
 */
public class ParameterInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl httpUrl = oldRequest.url();

        StringBuilder sb = new StringBuilder();
        int size = httpUrl.queryParameterNames().size();
        sb.append( httpUrl.url() + "  ");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sb.append(httpUrl.queryParameterName(i) + "=" + httpUrl.queryParameterValue(i));
                break;
            }
            sb.append(httpUrl.queryParameterName(i) + "=" + httpUrl.queryParameterValue(i) + ",");
        }
        Buffer buffer = new Buffer();
        if (oldRequest.body() != null)
        oldRequest.body().writeTo(buffer);
        sb.append(buffer.readUtf8());
        L.e("appHttp", ":Request " + sb.toString());

        // 新的请求
        Request.Builder builder = oldRequest.newBuilder();
        Request newRequest = builder.build();

        return chain.proceed(newRequest);
    }
}