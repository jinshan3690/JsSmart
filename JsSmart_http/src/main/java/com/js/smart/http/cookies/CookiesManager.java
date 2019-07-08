package com.js.smart.http.cookies;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {

    private static CookiesManager cookiesManager;
    private Context context;

    private CookiesManager(Context context) {
        this.context = context;
        cookieStore = new PersistentCookieStore(context);
    }

    public static CookiesManager getInstance(Context context){
        if (cookiesManager == null){
            synchronized (CookiesManager.class){
                if (cookiesManager == null){
                    return cookiesManager = new CookiesManager(context);
                }
            }
        }
        return cookiesManager;
    }

    private PersistentCookieStore cookieStore;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies  = cookieStore.get(url);
        return cookies;
    }
}