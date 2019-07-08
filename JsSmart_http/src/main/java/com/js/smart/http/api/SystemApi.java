package com.js.smart.http.api;



import com.js.smart.http.HttpConfig;
import com.js.smart.http.bean.Charges;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SystemApi {

    /**
     * @param type 0 注册 1 非注册
     */
    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> getSmsCode(@Field("rq") String rq, @Field("type") int type, @Field("mobile") String mobile);

    /**
     * @param type 0 注册 1 非注册
     */
    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> getEmailCode(@Field("rq") String rq, @Field("type") int type, @Field("email") String email);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> logout(@Field("rq") String rq, @Field("userName") String userName);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<Charges> getCharges(@Field("rq") String rq);

}
