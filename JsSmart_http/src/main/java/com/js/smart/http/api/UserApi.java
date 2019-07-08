package com.js.smart.http.api;


import com.js.smart.http.bean.User;
import com.js.smart.http.HttpConfig;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserApi {

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> login(@Field("rq") String rq, @Field("userName") String userName, @Field("password") String password);

    /**
     * @param registerType 手机号 0 邮箱 1
     */
    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> register(@Field("rq") String rq, @Field("userName") String userName, @Field("password") String password
            , @Field("registerType") int registerType, @Field("verifiCode") String verifiCode);

    /**
     * @param bindType 手机号 0 邮箱 1
     */
    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> changeUserName(@Field("rq") String rq, @Field("userName") String userName
            , @Field("bind") String bind, @Field("bindType") int bindType, @Field("verifiCode") String verifiCode);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> changePwd(@Field("rq") String rq, @Field("userName") String userName
            , @Field("newPassword") String newPassword, @Field("oldPassword") String oldPassword);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> forgot(@Field("rq") String rq, @Field("userName") String userName, @Field("newPassword") String newPassword, @Field("verifiCode") String verifiCode);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> forgotVerify(@Field("rq") String rq, @Field("userName") String userName, @Field("verifiCode") String verifiCode);

    @Multipart
    @POST(HttpConfig.BaseDomain)
    Observable<User> uploadAvatar(@Query("rq") String rq, @Query("userName") String userName, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> updateName(@Field("rq") String rq, @Field("userName") String userName, @Field("name") String name);

    /**
     * @param gender 男 1 女 0
     */
    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<String> updateGender(@Field("rq") String rq, @Field("userName") String userName, @Field("gender") int gender);

    @FormUrlEncoded
    @POST(HttpConfig.BaseDomain)
    Observable<User> userInfo(@Field("rq") String rq, @Field("userId") String userId);

}
