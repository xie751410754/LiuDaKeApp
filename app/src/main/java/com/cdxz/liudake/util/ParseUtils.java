package com.cdxz.liudake.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseUtils {
    /**
     * 将传递进来的json字符串转化为指定类型的对象返回
     *
     * @param jsonData:json字符串
     * @param clz:要转化的对象类型字节码
     * @return:转化后的对象
     */
    public static <T> T parseJsonObject(String jsonData, Class<T> clz) {
        T t = null;
        try {
            t = JSONObject.parseObject(jsonData, clz);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 将传递过来的json字符串转化为指定泛型类型的集合返回
     *
     * @param jsonData:json字符串
     * @param clz:要转化的集合返回类型字节码
     * @return:转化后的集合
     */
    public static <T> List<T> parseJsonArray(String jsonData, Class<T> clz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSONObject.parseArray(jsonData, clz);
        } catch (Exception e) {
        }
        return list;
    }
}
