package com.css.utils;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.TransformedMap;

import java.util.List;
import java.util.Map;

public abstract class MapUtil {
    /**
     * 把map的key转换成小写的key
     *
     * @param map
     * @return
     */
    public static Map<String, Object> changeKey2LowerCase(Map<String, Object> map) {
        return TransformedMap.decorateTransform(map, new Transformer() {
            @Override
            public Object transform(Object input) {
                return input != null ? String.valueOf(input).toLowerCase() : null;
            }
        }, null);
    }

    /**
     * 把map的key转换成小写的key
     *
     * @param list
     * @return
     */
    public static void changeKey2LowerCase(List<Map<String, Object>> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, changeKey2LowerCase(list.get(i)));
        }
    }
}

