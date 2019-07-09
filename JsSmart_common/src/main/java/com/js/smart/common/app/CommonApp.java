package com.js.smart.common.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.js.smart.common.R;
import com.js.smart.common.util.LanguageManage;
import com.js.smart.common.util.LocalManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import static me.jessyan.autosize.utils.LogUtils.isDebug;

/**
 * Created by Js on 2017/11/28.
 */

public abstract class CommonApp extends BaseApplication {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorTransparent, R.color.colorTransparent);//全局设置主题颜色
                return new ClassicsHeader(context)//指定为经典Header，默认是 贝塞尔雷达Header
//                        .setTimeFormat(new DynamicTimeFormat("更新于 %s"))
                        .setTextSizeTitle(0)
                        .setDrawableSize(0)
                        .setTextSizeTime(0);
//                        .setProgressResource(R.drawable.ico_index_dashboard);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter ClassicsFooter
                return new BallPulseFooter(context);
            }
        });
    }

    public static RequestOptions options;

    @Override
    public void onCreate() {
        super.onCreate();

        LanguageManage.setApplicationLanguage(this);
        LocalManager.getInstance(this);
        init();

        if (isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);

        //设置语言
        LanguageManage.changeLanguage(2);

    }

    protected abstract void init();

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LanguageManage.saveSystemLanguage();
        super.attachBaseContext(LanguageManage.setLocal(base));
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LanguageManage.onConfigurationChanged(getApplicationContext());
    }

}
