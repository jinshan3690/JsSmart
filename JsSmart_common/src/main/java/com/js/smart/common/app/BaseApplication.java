package com.js.smart.common.app;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.js.smart.common.util.SystemUtil;

public abstract class BaseApplication extends MultiDexApplication {

    public static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApplication.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null)
            context = this;

        if (SystemUtil.isMainProcess(context)) {
            mainThread();
        } else {
            workThread(SystemUtil.currentProcessName(context));
        }

    }


    /**
     * 主进程回调
     */
    protected void mainThread() {
    }

    /**
     * 其他进程回调
     */
    protected void workThread(String packageName) {
    }

}
