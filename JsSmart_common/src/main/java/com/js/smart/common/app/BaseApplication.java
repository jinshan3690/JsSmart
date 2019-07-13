package com.js.smart.common.app;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.js.smart.common.util.ProcessUtil;

public abstract class BaseApplication extends MultiDexApplication {

    public static Context context;

    public static Context getContext() {
        return context;
    }

    public BaseApplication() {
    }

    public BaseApplication(Application context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null)
            context = this;

        if (ProcessUtil.isMainProcess(context)) {
            mainThread();
        } else {
            workThread(ProcessUtil.currentProcessName(context));
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
