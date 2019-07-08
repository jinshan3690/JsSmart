package com.js.smart.http.bean;

import java.io.Serializable;

/**
 * Created by Js on 2016/8/25.
 */
public class Apk implements Serializable{

    private String id;

    /**
     * 强制升级
     */
    private String must;

    private String title;

    private String apkUrl;

    private int currentVersionCode;

    public String getMust() {
        return must;
    }

    public void setMust(String must) {
        this.must = must;
    }

    public int getCurrentVersionCode() {
        return currentVersionCode;
    }

    public void setCurrentVersionCode(int currentVersionCode) {
        this.currentVersionCode = currentVersionCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
