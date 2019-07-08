package com.js.smart.http;

import com.js.smart.http.api.OrderApi;
import com.js.smart.http.api.SystemApi;
import com.js.smart.http.api.UserApi;


public class Api {

    public static UserApi user() {
        return HttpApp.retrofit.create(UserApi.class);
    }

    public static OrderApi order() {
        return HttpApp.retrofit.create(OrderApi.class);
    }

    public static SystemApi system() {
        return HttpApp.retrofit.create(SystemApi.class);
    }

}
