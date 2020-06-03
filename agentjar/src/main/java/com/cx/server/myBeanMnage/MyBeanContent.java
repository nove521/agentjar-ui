package com.cx.server.myBeanMnage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyBeanContent {

    private static final Map<Class<?>, Object> bean = new ConcurrentHashMap<>();

    public void addBean(Object obj, Class<?> clazz) {
        if (clazz.isInstance(obj)) {
            bean.put(clazz, obj);
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(bean.get(clazz));
    }

    public Map<Class<?>, Object> getBeans() {
        return bean;
    }

    public void clear() {
        bean.clear();
    }

}
