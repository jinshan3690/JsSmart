package com.js.smart.http.req;

/**
 * Created by Js on 2017/12/12.
 */

public class BaseReq {

    /**
     * 数量
     */
    private int count = 10;

    /**
     * 起始
     */
    private int start;

    /**
     * 排序
     */
    private String sort;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
