package com.js.smart.http.config;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.js.smart.http.Api;
import com.js.smart.http.R;
import com.js.smart.http.api.SystemApi;
import com.js.smart.http.bean.Apk;
import com.js.smart.http.HttpConfig;
import com.js.smart.common.util.LocalManager;
import com.js.smart.common.util.NotificationUtil;
import com.js.smart.common.util.SystemUtil;
import com.js.smart.common.util.ThreadPool;
import com.js.smart.common.util.downloader.Downloader;
import com.js.smart.common.util.downloader.FileInfo;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Js on 2016/8/26.
 */
public class CheckUpdate {

    private Dialog dialog;

    private SystemApi systemApi;

    private Downloader downloader;

    private Context context;

    private CheckUpdate(Context context) {
        this.context = context;
        this.systemApi = Api.system();
    }

    public static CheckUpdate getInstance(Context context){
        return new CheckUpdate(context);
    }

    /**
     * 检查更新
     */
    public void checkUpdate(final Context context) {
//        systemApi.checkUpdate(SystemUtil.getPhoneInfo(context).getAppVersionCode())
//                .compose(((BaseCompatActivity)context).<ApkResp>bindToLifecycle())
//                .compose(this.<ApkResp>applySchedulers())
//                .subscribe(new HttpResult<ApkResp>(context) {
//                    @Override
//                    protected void onResult(final ApkResp result) {
////                        ((CheckUpdateActivity)context).hideLoading();
//                        dialog = new DefaultDialog(context)
//                                .showCustomDialog(result.getData().getTitle(), new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        if (v.getId() == R.id.btn1){
//                                            downloader(result.getData());
//                                        }else{
//                                            if (Boolean.valueOf(result.getData().getMust())) {
//                                                T.showShort("请下载最新版后使用");
//                                                return;
//                                            }
//                                        }
//                                        dialog.dismiss();
//                                        context.finish();
//                                    }
//                                });
//                        if (Boolean.valueOf(result.getData().getMust()))
//                            dialog.setCancelable(false);
//                    }
//
//                    @Override
//                    protected void onError(int code, String msg) {
////                        ((CheckUpdateActivity)context).hideLoading();
//                        context.finish();
//                    }
//                });

    }

    public <B> ObservableTransformer<B, B> applySchedulers(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 下载apk
     */
    private void downloader(Apk data){
        // 初始化一个downloader下载器
        downloader = new Downloader(HttpConfig.BaseUrl + data.getApkUrl(),
                LocalManager.getCachePath()+"/com.mqmfzl.apk", 4, context, mHandler);

        ThreadPool.threadPool(new Runnable() {
            @Override
            public void run() {
                FileInfo info = downloader.getDownloaderInfo();
                Message message = mHandler.obtainMessage();
                message.obj = info;
                mHandler.sendMessage(message);
                downloader.download();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        private int maxLength;
        private int length;
        private int percent = 100;
        private int base = 1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj instanceof  FileInfo){
                FileInfo info = (FileInfo) msg.obj;
                maxLength = info.getFileSize();
                length = info.getComplete();
                NotificationUtil.Build(context).notice(PendingIntent.FLAG_CANCEL_CURRENT)
                        .setContentTitle("正在下载"+ context.getResources().getString(R.string.app_name)+"APP").setContentText("下载中")
                        .setProgress(maxLength,length,false).setOngoing(true).build();
                return;
            }
            length += msg.arg1;

            int node = maxLength / percent * base;
            if (length >= node) {
                NotificationUtil.Build(context).notice(PendingIntent.FLAG_CANCEL_CURRENT)
                        .setContentTitle("正在下载" + context.getResources().getString(R.string.app_name) + "APP").setContentText("下载中")
                        .setProgress(maxLength, length, false).setOngoing(true).build();
                base += 1;
            }
            if (maxLength == length) {
                downloader.delete();

                NotificationUtil.Build(context).notice(LocalManager.getCachePath()+"/com.mqmfzl.apk")
                        .setContentTitle("下载完成").setContentText("点击安装")
                        .setProgress(maxLength,length,false).setAutoCancel(true).build();

                SystemUtil.installApk(context, LocalManager.getCachePath()+"/com.mqmfzl.apk");
            }
        }
    };

}
