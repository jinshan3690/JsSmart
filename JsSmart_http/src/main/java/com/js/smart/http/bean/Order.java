package com.js.smart.http.bean;

import java.io.Serializable;

public class Order implements Serializable {

    public Order() {
    }

    public Order(int order_status, String store_address) {
        this.order_status = order_status;
        this.store_address = store_address;
    }

    /**
     * store_id : 11
     * created_time : 2019-05-15 17:33:02
     * use_time : 3h6min
     * store_address : 中国上海
     * equipment_type : 22
     * borrow_time : 2019-05-15 17:33:02
     * pay_time : null
     * order_status : 2
     * payment_type : null
     * pay_status : 1
     * return_equip_id : 121221
     * actual_due : null
     * coupon_id : null
     * user_id : 122121212
     * retuen_time : 2019-05-15 20:39:24
     * amount_due : 12
     * id : 2
     * order_id : OR20190522095254
     * borrow_equip_id : 212121
     * equipment_id : 112333
     */

    private String store_id;
    private String created_time;
    private String use_time;
    private String store_address;
    private String equipment_type;
    private String borrow_time;
    private String pay_time;
    private int order_status;
    private Object payment_type;
    private int pay_status;
    private String return_equip_id;
    private String actual_due;
    private String coupon_id;
    private String user_id;
    private String retuen_time;
    private String amount_due;
    private String id;
    private String order_id;
    private String borrow_equip_id;
    private String equipment_id;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getEquipment_type() {
        return equipment_type;
    }

    public void setEquipment_type(String equipment_type) {
        this.equipment_type = equipment_type;
    }

    public String getBorrow_time() {
        return borrow_time;
    }

    public void setBorrow_time(String borrow_time) {
        this.borrow_time = borrow_time;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public String getReturn_equip_id() {
        return return_equip_id;
    }

    public void setReturn_equip_id(String return_equip_id) {
        this.return_equip_id = return_equip_id;
    }

    public String getRetuen_time() {
        return retuen_time;
    }

    public void setRetuen_time(String retuen_time) {
        this.retuen_time = retuen_time;
    }

    public String getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(String amount_due) {
        this.amount_due = amount_due;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBorrow_equip_id() {
        return borrow_equip_id;
    }

    public void setBorrow_equip_id(String borrow_equip_id) {
        this.borrow_equip_id = borrow_equip_id;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public Object getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(Object payment_type) {
        this.payment_type = payment_type;
    }

    public String getActual_due() {
        return actual_due;
    }

    public void setActual_due(String actual_due) {
        this.actual_due = actual_due;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(String equipment_id) {
        this.equipment_id = equipment_id;
    }
}
