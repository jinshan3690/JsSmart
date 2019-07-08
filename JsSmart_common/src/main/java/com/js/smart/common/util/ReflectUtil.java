package com.js.smart.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil {

    private static String[] types1 = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};
    private static String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean"
            , "java.lang.Character", "java.lang.Float", "java.lang.Double"
            , "java.lang.Long", "java.lang.Short", "java.lang.Byte"};

    /**
     * 获取对象中字段名 字段值
     */
    public static List<Map<String, Object>> reflectField(Object obj) {
        if (obj == null) return null;
        List<Map<String, Object>> list = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            Map<String, Object> map = new HashMap<>();
            fields[j].setAccessible(true);
            // 字段名
            map.put("name", fields[j].getName());
            for (int i = 0; i < types1.length; i++) {
                if (fields[j].getType().getName().equalsIgnoreCase(types1[i])
                        || fields[j].getType().getName().equalsIgnoreCase(types2[i])) {
                    try {
                        map.put("value", fields[j].get(obj));
                        System.out.print("name=" + fields[j].getName() +
                                ", value=" + fields[j].get(obj));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 获取对象中字段名
     */
    public static List<String> reflectFieldName(Object obj) {
        if (obj == null) return null;
        List<String> list = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            list.add(fields[j].getName());
        }
        return list;
    }

    /**
     * 获取对象中字段类型
     */
    public static List<Class> reflectFieldType(Object obj) {
        if (obj == null) return null;
        List<Class> list = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            list.add(fields[j].getType());
        }
        return list;
    }

    // type不能直接实例化对象，通过type获取class的类型，然后实例化对象
    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

}
