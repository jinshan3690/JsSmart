package com.js.smart.common.util.downloader;
 /**
  *创建一个下载信息的实体类
  */
 public class DownloadInfo {
     private int threadId;//下载器id
     private int startPos;//开始点
     private int endPos;//结束点
     private int completeSize;//完成度
     private String url;//下载器网络标识
     public DownloadInfo(int threadId, int startPos, int endPos,
             int completeSize,String url) {
         this.threadId = threadId;
         this.startPos = startPos;
         this.endPos = endPos;
         this.completeSize = completeSize;
         this.url=url;
     }
     public DownloadInfo() {
     }
     public String getUrl() {
         return url;
     }
     public void setUrl(String url) {
         this.url = url;
     }
     public int getThreadId() {
         return threadId;
     }
     public void setThreadId(int threadId) {
         this.threadId = threadId;
     }
     public int getStartPos() {
         return startPos;
     }
     public void setStartPos(int startPos) {
         this.startPos = startPos;
     }
     public int getEndPos() {
         return endPos;
     }
     public void setEndPos(int endPos) {
         this.endPos = endPos;
     }
     public int getCompleteSize() {
         return completeSize;
     }
     public void getCompleteSize(int completeSize) {
         this.completeSize = completeSize;
     }
 
     @Override
     public String toString() {
         return "DownloadInfo [threadId=" + threadId
                 + ", startPos=" + startPos + ", endPos=" + endPos
                 + ", completeSize=" + completeSize +"]";
     }
 }