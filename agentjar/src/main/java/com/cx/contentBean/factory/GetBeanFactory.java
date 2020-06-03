package com.cx.contentBean.factory;

import com.cx.agent.Session;
import com.cx.contentBean.BeanManage;
import com.cx.contentBean.SpringBeanManage;
import com.cx.contentBean.SumkBeanManage;

import java.util.Objects;

public class GetBeanFactory {

    public static final String[] platforms = new String[]{"spring", "sumk"};

    public static BeanManage generateBeanManage() {
        for (String platform : platforms) {
            BeanManage beanManage = generateBeanManage(platform);
            if (Objects.nonNull(beanManage)) {
                return beanManage;
            }
        }
        return null;
    }

    public static BeanManage generateBeanManage(String platform) {

        switch (platform) {
            case "spring":
                Class<?> beanContentClass = getBeanContentClass(SpringBeanManage.beanContentName);
                if (Objects.isNull(beanContentClass)) {
                    return null;
                }
                return new SpringBeanManage(beanContentClass);
            case "sumk":
                Class<?> beanContentClass2 = getBeanContentClass(SumkBeanManage.beanContentName);
                if (Objects.isNull(beanContentClass2)) {
                    return null;
                }
                return new SumkBeanManage(beanContentClass2);
        }

        return null;
    }

    private static Class<?> getBeanContentClass(String beanContentName) {
        return Session.getClassCache(beanContentName);
    }
}
