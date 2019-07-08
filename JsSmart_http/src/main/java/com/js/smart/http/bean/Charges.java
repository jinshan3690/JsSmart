package com.js.smart.http.bean;

import java.io.Serializable;

public class Charges implements Serializable {

    /**
     * amount_hour : 2
     * free_min : 5
     * content : $2/hour rental, the first 5 minutes of each hour are free, rounded up to the next hour. if not returned within 24 hours a $50 charge will be applied. Do Not Return.
     */

    private String amount_hour;
    private String free_min;
    private String content;

    public String getAmount_hour() {
        return amount_hour;
    }

    public void setAmount_hour(String amount_hour) {
        this.amount_hour = amount_hour;
    }

    public String getFree_min() {
        return free_min;
    }

    public void setFree_min(String free_min) {
        this.free_min = free_min;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
