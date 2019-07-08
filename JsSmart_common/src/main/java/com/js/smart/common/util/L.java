package com.js.smart.common.util;

import android.util.Log;

//Logcat统一管理类
public class L {

    static String TAG = "Qmx Log";
    static String className;
    static String methodName;
    static int lineNumber;
    static int TAG_MAX_LENGTH = 2000;
    static boolean isLogDebug = true;

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebuggable() {
        return isLogDebug;
    }

    /**
     * log格式
     */
    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append(className);
        buffer.append(":");
        buffer.append(methodName);
        buffer.append("(");
        buffer.append(lineNumber);
        buffer.append(" line)");
        buffer.append(log);

        return buffer.toString();
    }

    /**
     * 执行对象信息
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());

        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.i(TAG, "第"+i+"行  "+ msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.i(TAG, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void d(String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.d(TAG, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.d(TAG, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void e(String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.e(TAG, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.e(TAG, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void v(String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.v(TAG, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.v(TAG, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.i(tag, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.i(tag, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void d(String tag, String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.d(tag, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.d(tag, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void e(String tag, String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.e(tag, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.e(tag, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void v(String tag, String msg) {
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        int start = 0;
        int end = TAG_MAX_LENGTH;
        int strLength = msg.length();
        for (int i = 1; i < 100; i++) {
            if(strLength > end){
                Log.v(tag, "第"+i+"行  "+msg.substring(start, end));
                start = end;
                end = end + TAG_MAX_LENGTH;
            }else{
                Log.v(tag, "第"+i+"行  "+createLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

}