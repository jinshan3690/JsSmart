package com.js.smart.http.config.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


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
//            registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpApp.getOkHttpClient()));
        }

}