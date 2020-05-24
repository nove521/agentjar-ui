package com.cx.bean;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class SpringBeanManage implements BeanManage {

    public static final String beanContentName = "org.springframework.beans.factory.support.DefaultListableBeanFactory";

    private Class<?> beanContent;

    private Object beanFactory;

    public SpringBeanManage(Class<?> bc){
        beanContent = bc;
        this.beanFactory = getBeanFactory();
    }

    @Override
    public Object getBeanFactory() {

        if (Objects.isNull(beanContent)){
            return null;
        }

        if (Objects.nonNull(beanFactory)){
            return beanFactory;
        }

        try {
            Object beanFactory = beanContent.newInstance();
            Field field = beanFactory.getClass().getDeclaredField("serializableFactories");
            field.setAccessible(true);
            Object o = field.get(beanFactory);
            Map<String, Object> stringReferenceMap = (Map<String, Object>) o;
            Reference<Object> application = (Reference<Object>) stringReferenceMap.get("application");
            return application.get();

        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {

            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object getBean(Class<?> clazz) {
        try {
            Method getBean = this.beanFactory.getClass().getMethod("getBean", Class.class);
            return getBean.invoke(this.beanFactory, clazz);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
