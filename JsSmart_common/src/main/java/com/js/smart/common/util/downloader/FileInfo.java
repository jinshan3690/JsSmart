package com.js.smart.common.util.downloader;
 /**
  *自定义的一个记载下载器详细信息的类 
  */
 public class FileInfo {
     private int fileSize;//文件大小
     private int complete;//完成度
     private String url;//下载器标识
     public FileInfo(int fileSize, int complete, String url) {
         this.fileSize = fileSize;
         this.complete = complete;
         this.url = url;
     }
     public int getFileSize() {
         return fileSize;
     }
     public void setFileSize(int fileSize) {
         this.fileSize = fileSize;
     }
     public int getComplete() {
         return complete;
     }
     public void setComplete(int complete) {
         this.complete = complete;
     }
     public String getUrl() {
         return url;
     }
     public void setUrl(String url) {
         this.url = url;
     }
     @Override
     public String toString() {
         return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete
                 + ", url=" + url + "]";
     }
 }