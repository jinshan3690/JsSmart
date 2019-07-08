package com.js.smart.http.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created on 2019/4/22 13:06.
 *
 * @author pan
 * @version 1.0
 */
public class StringTypeAdapter implements JsonSerializer<String> , JsonDeserializer<String> {

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonObject())
            return null;
        if(json.isJsonArray())
            return null;

        String str = json.getAsString();
        if(str.contains(".")) {
            if (str.contains(".0")) {
                return str.substring(0, str.indexOf("."));
            }
        }

        return json.getAsString();
    }
}
