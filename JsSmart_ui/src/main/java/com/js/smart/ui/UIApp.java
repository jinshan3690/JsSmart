package com.js.smart.ui;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.js.smart.common.app.CommonApp;
import com.js.smart.common.init.strategy.InitStrategy;
import com.js.smart.http.HttpApp;

/**
 * Created by Js on 2017/11/28.
 */

public class UIApp extends HttpApp {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonApp.options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ico_default)
                .priority(Priority.HIGH)
                .error(R.mipmap.ico_default)
//                .override(300,300)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        UIApp.initStrategy = new InitStrategy(context, retrofit);
        executeStrategy();
    }

    @Override
    protected void init() {

    }

    public void executeStrategy(){
        UIApp.initStrategy.execute(R.raw.init_strategy);
    }

}
