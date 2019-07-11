package com.js.smart.http.bean;

import java.io.Serializable;

public class BaseResp<T> implements Serializable {

    private int succeedCode = 200;
    private int code;

    private T data;

    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSucceedCode() {
        return succeedCode;
    }

    public void setSucceedCode(int succeedCode) {
        this.succeedCode = succeedCode;
    }
}
