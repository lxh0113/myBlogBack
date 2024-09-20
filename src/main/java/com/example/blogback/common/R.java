package com.example.blogback.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    //这个代表 是否成功的消息
    private Integer code;

    //错误消息
    private String msg;

    //泛型来存储具体的数据 当然也是可以使用 Object 的
    private T data;

    //动态数据
    private Map map=new HashMap();

    public static <T> R<T> success(T object)
    {
        R<T> r=new R<T>();
        r.data= object;
        r.code=200;

        return r;
    }

    public static <T> R<T> error(String msg)
    {
        R<T> r=new R<T>();
        r.msg=msg;
        r.code=500;

        return r;
    }

    public R<T> add(String key,Object value)
    {
        this.map.put(key,value);
        return this;
    }
}
