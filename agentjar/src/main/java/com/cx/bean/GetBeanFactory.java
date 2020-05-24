package com.cx.bean;

import com.cx.agent.Session;

public class GetBeanFactory {

    public static BeanManage generateBeanManage(String platform){


        switch (platform){
            case "spring":
                Class<?> beanContentClass = getBeanContentClass(SpringBeanManage.beanContentName);
                System.out.println(beanContentClass);
                return new SpringBeanManage(beanContentClass);
        }

        return null;
    }

    private static Class<?> getBeanContentClass(String beanContentName){
        return Session.getClassCache(beanContentName);
    }
}
