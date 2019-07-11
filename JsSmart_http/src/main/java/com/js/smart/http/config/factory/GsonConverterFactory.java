package com.js.smart.http.config.factory;

/**
 * Created by Js on 2016/5/19.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class GsonConverterFactory extends Converter.Factory {

    private static GsonResponseBodyConverter.OnGsonResponseListener gsonResponseListener;
    /**
     * Create an instance using HouseAdapter default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by HouseAdapter header) will use UTF-8.
     */
    public static GsonConverterFactory create() {
        return create(new Gson());
    }

    public static GsonConverterFactory create(Gson gson) {
        return create(gson, gsonResponseListener);
    }
    public static GsonConverterFactory create(Gson gson, GsonResponseBodyConverter.OnGsonResponseListener gsonResponseListener) {
        GsonConverterFactory.gsonResponseListener = gsonResponseListener;
        return new GsonConverterFactory(gson);
    }

    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson, type).setGsonResponseListener(gsonResponseListener);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }
}

