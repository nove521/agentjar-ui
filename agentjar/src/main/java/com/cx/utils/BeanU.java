package com.cx.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BeanU {

    public static Map<String, Object> beanToMap(Object bean, boolean keepNull) throws IllegalAccessException {
        if (bean == null) {
            return Collections.emptyMap();
        }
        if (Map.class.isInstance(bean)) {
            return (Map<String, Object>) bean;
        }
        Field[] fields = bean.getClass().getFields();
        Map<String, Object> map = new HashMap<>();
        for (Field f : fields) {
            Object v = f.get(bean);
            if (!keepNull && v == null) {
                continue;
            }
            map.putIfAbsent(f.getName(), v);
        }
        return map;
    }
}
