package com.js.smart.http.config.factory;


import com.js.smart.http.bean.BaseResp;
import com.js.smart.http.bean.RespMsg;
import com.js.smart.http.exception.HttpException;
import com.js.smart.common.util.ReflectUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.js.smart.common.util.L;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Js on 2016/5/19.
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) {
        try {
            String response = value.string();

            L.e("appHttp", ":Response " + type.toString() + " = " + response);
            try {
                RespMsg t = new Gson().fromJson(response, RespMsg.class);

                if (t.getCode() != 200) {
                    throw new HttpException(t.getMessage(), t.getCode());
                }
            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
            }

            BaseResp baseResp = new Gson().fromJson(response, BaseResp.class);
            if (baseResp.getData() == null)
                return (T) ReflectUtil.getRawType(type).newInstance();
            return gson.fromJson(new Gson().toJson(baseResp.getData()), type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage(), -33);
        }
    }



}