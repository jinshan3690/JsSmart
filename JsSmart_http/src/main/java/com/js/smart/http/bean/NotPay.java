package com.js.smart.http.bean;

public class NotPay {


    /**
     * isPay : false
     * order : null
     */

    private boolean isPay;
    private Order order;

    public boolean isIsPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
