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
                BaseResp<String> failed = gsonResponseListener.failed(response);

                if (failed.getCode() != failed.getSucceedCode()) {
                    throw new HttpException(failed.getMessage(), failed.getCode());
                }

                BaseResp succeed = gsonResponseListener.succeed(response);
                if (succeed.getData() == null)
                    return (T) ReflectUtil.getRawType(type).newInstance();
                return gson.fromJson(new Gson().toJson(succeed.getData()), type);
            }else{
                return gson.fromJson(response, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage(), -9999);
        }
    }

    public void setGsonResponseListener(OnGsonResponseListener gsonResponseListener) {
        this.gsonResponseListener = gsonResponseListener;
    }

    public interface OnGsonResponseListener{
        BaseResp<String> failed(String json);
        BaseResp succeed(String json);
    }


}