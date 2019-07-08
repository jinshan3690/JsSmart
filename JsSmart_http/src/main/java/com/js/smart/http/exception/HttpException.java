package com.js.smart.http.exception;

/**
 * Created by Js on 2016/5/24.
 */
public class HttpException extends RuntimeException {

    private int code;

    public HttpException(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
