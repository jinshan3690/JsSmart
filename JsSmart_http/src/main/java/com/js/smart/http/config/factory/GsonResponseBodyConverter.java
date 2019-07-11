package com.js.smart.http.config.factory;


import com.js.smart.http.bean.BaseResp;
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
public final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;
    private OnGsonResponseListener gsonResponseListener;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) {
        try {
            String response = value.string();

            L.e("appHttp", ":Response " + type.toString() + " = " + response);

            if (gsonResponseListener != null) {
                BaseResp gsonResponse = gsonResponseListener.response(response);

                if (gsonResponse.getCode() != gsonResponse.getSucceedCode()) {
                    throw new HttpException(gsonResponse.getMessage(), gsonResponse.getCode());
                }

                gsonResponse = gsonResponseListener.response(response);
                if (gsonResponse.getData() == null)
                    return (T) ReflectUtil.getRawType(type).newInstance();
                return gson.fromJson(new Gson().toJson(gsonResponse.getData()), type);
            }else{
                return gson.fromJson(response, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage(), -9999);
        }
    }

    public GsonResponseBodyConverter setGsonResponseListener(OnGsonResponseListener gsonResponseListener) {
        this.gsonResponseListener = gsonResponseListener;
        return this;
    }

    public interface OnGsonResponseListener{
        BaseResp response(String json);
    }


}