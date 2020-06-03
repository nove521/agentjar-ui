package com.cx.contentBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SumkBeanManage implements BeanManage {

    public static final String beanContentName = "org.yx.bean.IOC";

    private Class<?> IOCUtils;

    public SumkBeanManage(Class<?> ioc) {
        this.IOCUtils = ioc;
    }

    @Override
    public Object getBeanFactory() {
        return null;
    }

    @Override
    public Object getBean(Class<?> clazz) {
        try {
            Method getBean = IOCUtils.getMethod("get", Class.class);
            return getBean.invoke(this.IOCUtils, clazz);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }
}
