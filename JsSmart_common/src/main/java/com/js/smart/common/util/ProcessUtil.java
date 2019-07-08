package com.js.smart.common.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ProcessUtil {

    /**
     * 当前进程是否是主进程
     */
    public static boolean isMainProcess(Context context) {
        return currentProcessName(context).equals(context.getPackageName());
    }

    /**
     * 当前进程名称
     */
    public static String currentProcessName(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfo = am.getRunningAppProcesses();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfo) {
            if (info.pid == myPid) {
                return info.processName;
            }
        }
        return "";
    }

}
