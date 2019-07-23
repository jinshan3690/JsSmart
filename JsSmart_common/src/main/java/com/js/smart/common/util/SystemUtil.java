package com.js.smart.common.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.Serializable;
import java.util.List;


/**
 * Created by Js on 2016/5/23.
 */
public class SystemUtil {

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static void updatePhoto(Context context, String path) {
        //通知相册更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + path)));
    }

    /**
     * 复制到剪切板
     */
    public static void copy(Context context, String copy) {
        //获取剪贴版
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        //第一个参数只是一个标记，随便传入。
        //第二个参数是要复制到剪贴版的内容
        ClipData clip = ClipData.newPlainText("copy text", copy);
        //传入clipdata对象.
        clipboard.setPrimaryClip(clip);
    }

    /**
     * 打电话
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(AppCompatActivity context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        context.startActivity(intent);
    }

    /**
     * 发邮件
     */
    public static void sendEmail(Context context, String emailAddress) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse(String.format("mailto:%s", emailAddress)));
        data.putExtra(Intent.EXTRA_SUBJECT, "");
        data.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(data);
    }

    /**
     * 发送短信
     */
    public static void sendSms(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
                .parse("smsto:" + phone));
        // 如果需要将内容传过去增加如下代码
//        intent .putExtra("sms_body", body);
        context.startActivity(intent);
    }

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

    /**
     * 版本名
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 版本号
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 获取手机信息
     */
    public static PhoneInfo getPhoneInfo(Context context) {
        // 应用的版本名称和版本号
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        PhoneInfo phoneInfo = new PhoneInfo();
        try {
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            phoneInfo.setAppVersion(pi.versionName);// + '_' + pi.versionCode
            phoneInfo.setAppVersionCode(pi.versionCode);// + '_' + pi.versionCode
            phoneInfo.setPhoneVersion(Build.VERSION.RELEASE);//Build.VERSION.SDK_INT
            phoneInfo.setPhoneName(Build.MANUFACTURER);
            phoneInfo.setPhoneType(Build.MODEL);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return phoneInfo;
    }

    /**
     * 安装apk
     */
    public static void installApk(Context context, File file) {
        installApk(context, Uri.fromFile(file));
    }

    public static void installApk(Context context, Uri uri) {
        installApk(context, uriToFile(context, uri));
    }

    public static void installApk(Context context, String path) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".fileProvider", new File(path));
            install.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(install);
    }

    public static File uriToFile(Context context, Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = context.getContentResolver().query(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }

    public static class PhoneInfo implements Serializable {

        private String appVersion;
        private int appVersionCode;
        private String phoneVersion;
        private String phoneName;
        private String phoneType;
        private String customerId;

        public int getAppVersionCode() {
            return appVersionCode;
        }

        public void setAppVersionCode(int appVersionCode) {
            this.appVersionCode = appVersionCode;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getPhoneVersion() {
            return phoneVersion;
        }

        public void setPhoneVersion(String phoneVersion) {
            this.phoneVersion = phoneVersion;
        }

        public String getPhoneName() {
            return phoneName;
        }

        public void setPhoneName(String phoneName) {
            this.phoneName = phoneName;
        }

        public String getPhoneType() {
            return phoneType;
        }

        public void setPhoneType(String phoneType) {
            this.phoneType = phoneType;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
    }

    /**
     * 网络状态
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
