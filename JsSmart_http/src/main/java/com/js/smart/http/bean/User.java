package com.js.smart.http.bean;



import java.io.Serializable;

/**
 * Created by Js on 2017/9/1.
 */


public class User implements Serializable{


    /**
     * id : 10
     * userName : 8615618061207
     * phoneNum : 8615618061207
     * eMail : null
     * registerTime : 2019-05-15 16:06:45
     * lastLoginTime : 2019-05-27 15:25:04
     * gender : null
     * picPath : null
     * token : 7c0561dd80e04b4aa9d99577ddef17ea
     * tokenExpires : 2592000
     */

    private String id;
    private String userName;
    private String phoneNum;
    private String eMail;
    private String registerTime;
    private String lastLoginTime;
    private Integer gender;
    private String picPath;
    private String token;
    private String distributorNo;
    private int tokenExpires;

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(int tokenExpires) {
        this.tokenExpires = tokenExpires;
    }
}
