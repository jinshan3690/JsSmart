package com.js.smart.http.bean;


import java.io.Serializable;

/**
 * Created by Js on 2017/9/1.
 */


public class Shop implements Serializable {

    /**
     * id : 8
     * shopName : 盛春商厦店
     * shopAddress : 上海市长宁区愚园路1391号
     * businessHour : 早8点晚10点
     * shopImgPath : http://img.e-chinacycle.cn/201906/1560577258544.jpg
     * createdBy : 1135498837289402368
     * createdTime : 2019-06-15 13:42:29
     * updatedBy : null
     * updatedTime : null
     * longitude : 121.422747
     * latitude : 31.2187319
     * userid : 1135498837289402368
     * shopinPhoto : http://img.e-chinacycle.cn/201906/1560577265319.jpg
     * shopoutPhoto : http://img.e-chinacycle.cn/201906/1560577268789.jpg
     * availableCount : 0
     * slotsCount : 12
     */

    private String id;
    private String shopName;
    private String shopAddress;
    private String businessHour;
    private String shopImgPath;
    private String createdBy;
    private String createdTime;
    private String updatedBy;
    private String updatedTime;
    private String longitude;
    private String latitude;
    private String userid;
    private String shopinPhoto;
    private String shopoutPhoto;
    private String ratesContent;
    private String label;
    private int availableCount;
    private int slotsCount;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRatesContent() {
        return ratesContent;
    }

    public void setRatesContent(String ratesContent) {
        this.ratesContent = ratesContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getBusinessHour() {
        return businessHour;
    }

    public void setBusinessHour(String businessHour) {
        this.businessHour = businessHour;
    }

    public String getShopImgPath() {
        return shopImgPath;
    }

    public void setShopImgPath(String shopImgPath) {
        this.shopImgPath = shopImgPath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getShopinPhoto() {
        return shopinPhoto;
    }

    public void setShopinPhoto(String shopinPhoto) {
        this.shopinPhoto = shopinPhoto;
    }

    public String getShopoutPhoto() {
        return shopoutPhoto;
    }

    public void setShopoutPhoto(String shopoutPhoto) {
        this.shopoutPhoto = shopoutPhoto;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getSlotsCount() {
        return slotsCount;
    }

    public void setSlotsCount(int slotsCount) {
        this.slotsCount = slotsCount;
    }
}
