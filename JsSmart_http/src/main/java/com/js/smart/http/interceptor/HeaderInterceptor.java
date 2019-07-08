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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 新的请求
        String token = LocalManager.getInstance().getShareString("token");
        Request.Builder builder = oldRequest.newBuilder();
        if (StringUtils.isNotBlank(token)) {
            builder.addHeader("req_token", token);
        }
        Request newRequest = builder.build();


        return chain.proceed(newRequest);
    }
}