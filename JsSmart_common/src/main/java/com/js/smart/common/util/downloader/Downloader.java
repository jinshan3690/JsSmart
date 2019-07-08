package com.js.smart.common.util.downloader;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Downloader {
    private String url;// 下载的地址
    private String localFile;// 保存路径
    private int threadCount;// 线程数
    private Handler mHandler;// 消息处理器
    private int fileSize;// 所要下载的文件的大小
    private Context context;
    private List<DownloadInfo> info;// 存放下载信息类的集合
    private static final int INIT = 1;//定义三种下载的状态：初始化状态，正在下载状态，暂停状态
    private static final int DOWNLOADING = 2;
    private static final int PAUSE = 3;
    private int state = INIT;

    public Downloader(String url, String localFile, int threadCount,
                      Context context, Handler mHandler) {
        this.url = url;
        this.localFile = localFile;
        this.threadCount = threadCount;
        this.mHandler = mHandler;
        this.context = context;
    }

    /**
     * 得到downloader里的信息
     * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中
     * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器
     */
    public FileInfo getDownloaderInfo() {
        if (isFirst(url)) {
            init();
            int range = fileSize / threadCount;
            info = new ArrayList<>();
            for (int i = 0; i < threadCount - 1; i++) {
                DownloadInfo download = new DownloadInfo(i, i * range, (i + 1) * range - 1, 0, url);
                info.add(download);
            }
            DownloadInfo download = new DownloadInfo(threadCount - 1, (threadCount - 1) * range, fileSize - 1, 0, url);
            info.add(download);
            //保存info中的数据到数据库
            DownloaderDao.getInstance(context).saveInfo(info);
            //创建一个LoadInfo对象记载下载器的具体信息
            return new FileInfo(fileSize, 0, url);
        } else {
            //得到数据库中已有的url的下载器的具体信息
            info = DownloaderDao.getInstance(context).getInfo(url);
            int size = 0;
            int completeSize = 0;
            for (DownloadInfo download : info) {
                completeSize += download.getCompleteSize();
                size += download.getEndPos() - download.getStartPos() + 1;
            }
            return new FileInfo(size, completeSize, url);
        }

    }

    /**
     * 初始化
     */
    private void init() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(localFile);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            // 本地访问文件
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是第一次 下载
     */
    private boolean isFirst(String url) {
        return DownloaderDao.getInstance(context).isHasInfo(url);
    }

    /**
     * 利用线程开始下载数据
     */
    public void download() {
        if (info != null)
        if (state == DOWNLOADING)
            return;
        state = DOWNLOADING;
        for (DownloadInfo download : info) {
            new Multithreading(download.getThreadId(), download.getStartPos(),
                    download.getEndPos(), download.getCompleteSize(),
                    download.getUrl()).start();
        }
    }

    public class Multithreading extends Thread {
        private int threadId;
        private int startPos;
        private int endPos;
        private int completeSize;
        private String url;

        public Multithreading(int threadId, int startPos, int endPos,
                              int completeSize, String url) {
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.completeSize = completeSize;
            this.url = url;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;
            try {
                URL url = new URL(this.url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置范围，格式为Range：bytes x-y;
                connection.setRequestProperty("Range", "bytes=" + (startPos + completeSize) + "-" + endPos);

                randomAccessFile = new RandomAccessFile(localFile, "rwd");
                randomAccessFile.seek(startPos + completeSize);
                // 将要下载的文件写到保存在保存路径下的文件中
                is = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    completeSize += length;
                    // 更新数据库中的下载信息
                    DownloaderDao.getInstance(context).updateInfo(threadId, completeSize, this.url);
                    // 用消息将下载信息传给进度条，对进度条进行更新
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = this.url;
                    message.arg1 = length;
                    mHandler.sendMessage(message);
                    if (state == PAUSE) {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否正在下载
     */
    public boolean isDownloading() {
        return state == DOWNLOADING;
    }

    /**
     * 删除数据库中url对应的下载器信息
     */
    public void delete() {
        DownloaderDao.getInstance(context).delete(url);
    }

    /**
     * 设置暂停
     */
    public void pause() {
        state = PAUSE;
    }

    /**
     * 重置下载状态
     */
    public void reset() {
        state = INIT;
    }

    public String getUrl() {
        return url;
    }
}