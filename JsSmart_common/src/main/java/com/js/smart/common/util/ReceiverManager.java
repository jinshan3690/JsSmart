package com.js.smart.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import com.js.smart.common.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 广播管理器，仅用于App内部广播，目的是为了代替观测者模式，它可以支持多进程
 */
public class ReceiverManager {

    public static List<BroadcastReceiver> receivers = new ArrayList<>();

    public enum Action{
        ACTION_Logout, ACTION_Home
    }

    /**
     * 注册广播
     */
    public static void registerReceiver(Context context, Action action,
                                        BroadcastReceiver receiver) {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(R.class.getPackage().getName()
                + action.toString());
        context.registerReceiver(receiver, myIntentFilter);
        receivers.add(receiver);
    }

    /**
     * 移除广播
     */
    public static void unregisterReceiver(Context context,
                                          BroadcastReceiver receiver) {
        try {
            context.unregisterReceiver(receiver);
            receivers.remove(receiver);
        } catch (Exception e) {
        }
    }

    /**
     * 移除所有广播
     */
    public static void unregisterReceiverAll(Context context) {
        try {
            int len = receivers.size();
            for (int i = 0; i < len; i++) {
                context.unregisterReceiver(receivers.get(i));
            }
            receivers.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送广播
     */
    public static void sendBroadcast(Context context, Action action,
                                     Intent intent) {
        if (intent == null) {
            intent = new Intent(R.class.getPackage().getName()
                    + action.toString());
        } else {
            intent.setAction(R.class.getPackage().getName() + action.toString());
        }
        context.sendBroadcast(intent);
    }

    /**
     * 发送动态广播
     */
    public static void sendBroadcast(Context context, Action action) {
        sendBroadcast(context, action, null);
    }

    public static boolean hasAction(Action action, Intent intent) {
        return (R.class.getPackage().getName()+ action.toString()).equals(intent.getAction());
    }
}
