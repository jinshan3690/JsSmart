package com.js.smart.common.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.js.smart.common.R;

import java.io.File;


public class NotificationUtil {

    private Context context;
    private static NotificationUtil util;
    private static NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private static final int DEFAULT_ID = 1;
    private static String channelId;

    public NotificationUtil(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        channelId = android.R.class.getPackage().getName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 通知渠道的id
            String id = channelId;
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "JsSmart APP";
            // 用户可以看到的通知渠道的描述
            String description = "JsSmart APP Notify";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    public static NotificationUtil Build(Context context) {
        if (util == null) {
            synchronized (NotificationUtil.class) {
                util = new NotificationUtil(context);
            }
        }
        init();
        return util;
    }

    private static void init(){
        // 设置Notification
        builder = new NotificationCompat.Builder(util.context, channelId);
        builder.setSmallIcon(R.mipmap.ico_logo)
                .setContentTitle("通知")
                .setContentText("这是一条消息");
//                .setDefaults(Notification.DEFAULT_ALL);
    }

    public static NotificationUtil Build() {
        return util;
    }

    public NotificationUtil notice() {
        return notice(null, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public NotificationUtil notice(int mode) {
        return notice(null, mode);
    }

    public NotificationUtil notice(Class cla) {
        return notice(cla, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public NotificationUtil notice(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".fileProvider", new File(path));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        return util;
    }

    public NotificationUtil notice(Class cla, int mode) {
        if (cla != null) {
            Intent intent = new Intent(context, cla);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, mode);
            builder.setContentIntent(pendingIntent);
        }
        return util;
    }

    public NotificationUtil build() {
        notificationManager.notify(DEFAULT_ID, builder.build());
        return util;
    }

    public NotificationUtil build(int id) {
        notificationManager.notify(id, builder.build());
        return util;
    }

    /**
     * 设置通道id
     */
    public static void setChannelId(String channelId) {
        NotificationUtil.channelId = channelId;
    }

    /**
     * 设置时间
     */
    public NotificationUtil setWhen() {
        builder.setWhen(System.currentTimeMillis());
        return util;
    }

    /**
     * 可以取消通知
     */
    public NotificationUtil cancel() {
        notificationManager.cancel(DEFAULT_ID);
        return util;
    }

    public NotificationUtil cancel(int id) {
        notificationManager.cancel(id);
        return util;
    }

    /**
     * 点击取消 true
     */
    public NotificationUtil setAutoCancel(boolean cancel) {
        builder.setAutoCancel(cancel);
        return util;
    }

    /**
     * 滑动删除 false
     */
    public NotificationUtil setOngoing(boolean cancel) {
        builder.setOngoing(cancel);
        return util;
    }

    /**
     * 设置震动
     */
    public NotificationUtil setVibrate() {
        return setVibrate(new long[]{400, 300, 400, 300});
    }

    public NotificationUtil setVibrate(long[] vibrate) {
        builder.setVibrate(vibrate);
        return util;
    }

    /**
     * 设置大图标
     */
    public NotificationUtil setSmallIcon(int id) {
        builder.setSmallIcon(id);
        return util;
    }

    /**
     * 设置小图标
     */
    public NotificationUtil setLargeIcon(Bitmap bitmap) {
        builder.setLargeIcon(bitmap);
        return util;
    }

    /**
     * 设置数量
     */
    public NotificationUtil setNumber(int count) {
        builder.setNumber(count);
        return util;
    }

    /**
     * 设置内容
     */
    public NotificationUtil setContentText(String content) {
        builder.setContentText(content);
        return util;
    }

    /**
     * 设置标题
     */
    public NotificationUtil setContentTitle(String title) {
        builder.setContentTitle(title);
        return util;
    }

    /**
     * 设置声音
     */
    public NotificationUtil setSound(int raw) {
        builder.setSound(Uri.parse("android.resource://"
                + context.getPackageName() + "/" + raw));
        return util;
    }

    public NotificationUtil setSound(Uri uri) {
        builder.setSound(uri);
        return util;
    }

    /**
     * 设置在第一个通知到达时显示在状态栏中的文本
     */
    public NotificationUtil setTicker(String content) {
        builder.setTicker(content);
        return util;
    }

    /**
     * 设置在第一个通知到达时显示在状态栏中的文本
     * Color.BLUE, 0, 1
     */
    public NotificationUtil setLights(int a, int b, int c) {
        builder.setLights(a, b, c);
        return util;
    }

    /**
     * 设置在第一个通知到达时显示在状态栏中的文本
     * indeterminate true 进度条为流动
     */
    public NotificationUtil setProgress(int max, int progress, boolean indeterminate) {
        builder.setProgress(max, progress, indeterminate);
        return util;
    }

    /**
     * 设置在第一个通知到达时显示在状态栏中的文本
     * Color.BLUE, 0, 1
     */
    public NotificationUtil setRemoteViews(int layout) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layout);
        builder.setContent(remoteViews);
        return util;
    }

    public NotificationUtil setRemoteViews(RemoteViews view) {
        builder.setContent(view);
        return util;
    }
}
