package com.js.smart.http.bean;

import java.io.Serializable;

public class BaseResp<T> implements Serializable {

    private int code;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
