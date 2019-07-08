package com.js.smart.http.api;


import com.js.smart.http.HttpConfig;
import com.js.smart.http.bean.Distributor;
import com.js.smart.http.bean.IsReturn;
import com.js.smart.http.bean.NotPay;
import com.js.smart.http.bean.Order;
import com.js.smart.http.bean.Page;
import com.js.smart.http.bean.SearchShop;
import com.js.smart.http.bean.SearchShopLabel;
import com.js.smart.http.bean.Shop;
import com.js.smart.http.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderApi {

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> addOrder(@Field("rq") String rq, @Field("equipment_type") String equipmentType, @Field("equipment_id") String equipmentId,
                              @Field("borrow_equip_id") String borrowEquipId, @Field("user_id") String userId,
                              @Field("store_id") String storeId, @Field("store_address") String storeAddress);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> updateOrder(@Field("rq") String rq, @Field("id") String id, @Field("equipment_id") String equipmentId,
                                 @Field("return_equip_id") String returnEquipId, @Field("amount_due") String amountDue);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)// 进行中 1    结束 2     订单报失4
    Observable<Page<Order>> getOrder(@Field("rq") String rq, @Field("user_id") String userId, @Field("page") int page);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)// 进行中 1    结束 2     订单报失4
    Observable<Order> getOrderInfo(@Field("rq") String rq, @Field("id") String orderId);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> sendEmail(@Field("rq") String rq, @Field("id") String id, @Field("email") String email);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<Order> eQuipmentMiss(@Field("rq") String rq, @Field("order_id") String orderId, @Field("pay_token") String payToken);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<List<SearchShop>> getNearbyShops(@Field("rq") String rq, @Field("longitude") String longitude, @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<SearchShopLabel> getSearchShops(@Field("rq") String rq, @Field("label") String label, @Field("longitude") String longitude, @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<Shop> getShopById(@Field("rq") String rq, @Field("shop_id") String shopId);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<Shop> getShopByPowerBankNo(@Field("rq") String rq, @Field("power_bank_no") String powerBankNo);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> generateOrder(@Field("rq") String rq, @Field("user_id") String userId, @Field("store_id") String shopId,
                                       @Field("store_address") String shopAddress, @Field("power_bank_no") String powerBankNo);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<IsReturn> returnDevice(@Field("rq") String rq, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<IsReturn> isBorrow(@Field("rq") String rq, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<NotPay> notPay(@Field("rq") String rq, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<Distributor> joinDistributor(@Field("rq") String rq, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> updateDistributorNo(@Field("rq") String rq, @Field("user_id") String userId, @Field("distributor_no") String distributor_no);


}
