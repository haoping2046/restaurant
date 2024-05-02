package com.hp.restaurant.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic Response Class
 * @param <T>
 */
@Data
public class R<T> implements Serializable {

    private Integer code; //1 is success, 0 or others are fail

    private String msg; //error msg

    private T data; // normal data

    private Map map = new HashMap(); // dynamic data

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
