package com.js.smart.http.interceptor;



import com.js.smart.common.util.LocalManager;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Js on 2016/5/20.
 */
public class HeaderInterceptor implements Interceptor {

    public static String[] headers;
    public static String[] values;

    public static void setHeaders(String[] headers, String[] values) {
        HeaderInterceptor.headers = headers;
        HeaderInterceptor.values = values;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 新的请求
        Request.Builder builder = oldRequest.newBuilder();
        if (headers != null && headers.length > 0) {
            for (int i = 0; i < headers.length; i++) {
                builder.addHeader(headers[i], values[i]);
            }
        }
        Request newRequest = builder.build();


        return chain.proceed(newRequest);
    }
}