package com.js.smart.http.bean;



import com.multilevel.treelist.Node;

import java.io.Serializable;

/**
 * Created by Js on 2017/9/1.
 */


public class Help extends Node implements Serializable{

//   "mobile":"13816253454","id":"282","token":"6222a05966e6da45f336ee2b654f9c83"
    private String id;

    private String mobile;

    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
