package com.wyd.aroundaddress.mgson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TypeAdapters;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @description
 * 使用方法
 * @author wyd
 * @version 1.0
 */
public class MGson {
    public static Gson newGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Class builder = (Class) gsonBuilder.getClass();
        Field f = null;
        try {
            //通过反射得到构造器
            f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            final Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);//得到此属性的值
            //注册String类型处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(String.class,GsonTools.stringTypeAdapter()));
            //注册int.class, Integer.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, GsonTools.longAdapter(0)));
            //注册short.class, Short.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(short.class, Short.class, GsonTools.longAdapter(1)));
            //注册long.class, Long.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, GsonTools.longAdapter(2)));
            //注册double.class, Double.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, GsonTools.longAdapter(3)));
            //注册float.class, Float.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, GsonTools.longAdapter(4)));
            //注册反射对象的处理器
            gsonBuilder.registerTypeAdapterFactory(new ReflectiveTypeAdapterFactory(new ConstructorConstructor(val), FieldNamingPolicy.IDENTITY, Excluder.DEFAULT));
            //注册集合的处理器
            gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonBuilder.create();
    }
}
