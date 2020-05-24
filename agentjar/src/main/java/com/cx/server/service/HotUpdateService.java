package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.javaCompiler.MyCompiler;
import com.cx.bean.BeanManage;
import com.cx.bean.GetBeanFactory;
import com.cx.ognl.OgnlHolder;
import com.cx.utils.ClassUtils;
import ognl.OgnlException;
import org.yx.annotation.Bean;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Bean
public class HotUpdateService {

    private static final Instrumentation INS = Session.getIns();

    public String updateClassByString(String className, String javaCode) {
        Class<?> clazz = Session.getClassCache(className);
        if (Objects.isNull(clazz)) {
            return "找不到类";
        }
        int hashcode1 = clazz.getClassLoader().hashCode();
        MyCompiler compiler = new MyCompiler(ClassUtils.generateClassLoad(clazz));

        Map<String, byte[]> clazzByteMap = compiler.start(className, javaCode);

        Set<String> keySet = clazzByteMap.keySet();
        List<ClassDefinition> classDefinitions = new ArrayList<>();
        for (String key : keySet) {
            byte[] clazzByte = clazzByteMap.get(key);
            if (clazzByte.length > 0 && hashcode1 == Session.getClassCache(key).getClassLoader().hashCode()) {
                Class<?> aClass = Session.getClassCache(key);
                cacheOldClassResource(clazz);
                ClassDefinition classDefinition = new ClassDefinition(aClass, clazzByte);
                classDefinitions.add(classDefinition);
            }
        }

        if (classDefinitions.isEmpty()) {
            return "no";
        }

        try {
            INS.redefineClasses(classDefinitions.toArray(new ClassDefinition[0]));
            cacheNewClassResource(className, javaCode);
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    public void resetClass() {
        Map<Class<?>, byte[]> cacheClassByte = Session.getCacheClassByte();

        Set<Class<?>> keySet = cacheClassByte.keySet();
        if (keySet.isEmpty()) {
            return;
        }

        List<ClassDefinition> classDefinitions = new ArrayList<>();

        for (Class<?> clazz : keySet) {
            ClassDefinition classDefinition = new ClassDefinition(clazz, cacheClassByte.get(clazz));
            classDefinitions.add(classDefinition);
        }
        try {
            INS.redefineClasses(classDefinitions.toArray(new ClassDefinition[0]));
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            e.printStackTrace();
        } finally {
            Session.clearCacheClassByte();
            Session.clearCacheNewClassByte();
        }
    }

    /**
     * 保存原始class文件
     */
    private void cacheOldClassResource(Class<?> clazz) {
        Map<Class<?>, byte[]> cacheClassByte = Session.getCacheClassByte();
        if (Objects.isNull(cacheClassByte.get(clazz))) {
            byte[] bytes = ClassUtils.getByteByClass(clazz);
            if (Objects.nonNull(bytes)) {
                cacheClassByte.put(clazz, bytes);
            }
        }
    }

    /**
     * 保存最新更改的class文件
     *
     * @param className
     */
    private void cacheNewClassResource(String className, String javaCode) {
        Map<String, String> newClassByte = Session.getCacheNewClassByte();
        newClassByte.put(className, javaCode);
    }

    public String ognltest(){

        try {
            return OgnlHolder.sayHello();
        } catch (OgnlException e) {
            e.printStackTrace();
            return "no";
        }
    }

    public Object invoke(String className, String methodName){
        BeanManage beanManage = GetBeanFactory.generateBeanManage("spring");
        Class<?> aClass = Session.getClassCache(className);
        Object bean = beanManage.getBean(aClass);

        if (Objects.isNull(bean)){
            return null;
        }

        try {
            Method method = bean.getClass().getMethod(methodName);
            return method.invoke(bean);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
