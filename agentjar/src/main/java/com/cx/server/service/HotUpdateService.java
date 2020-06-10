package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.contentBean.BeanManage;
import com.cx.contentBean.factory.GetBeanFactory;
import com.cx.enums.SystemMessage;
import com.cx.javaCompiler.MyCompiler;
import com.cx.server.ann.Obj;
import com.cx.utils.ClassUtils;
import com.cx.utils.IoUtils;
import com.cx.utils.StrUtils;
import com.google.gson.JsonSyntaxException;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Obj
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

    @SuppressWarnings("unchecked")
    public Object invoke(String className, String methodName, String paramsJson) {
        BeanManage beanManage = GetBeanFactory.generateBeanManage();
        if (Objects.isNull(beanManage)) {
            return SystemMessage.NO_FOUND_BEAN.getVal();
        }
        System.out.println("------+-------");
        Class<?> aClass = Session.getClassCache(className);
        Object bean = beanManage.getBean(aClass);
        List<String> params = StrUtils.json().fromJson(paramsJson, List.class);
        if (Objects.isNull(bean)) {
            return SystemMessage.NO_FOUND_BEAN.getVal();
        }

        try {
            Method method = ClassUtils.matchMethod(bean.getClass().getMethods(), methodName);
            if (Objects.nonNull(method)) {
                List<Object> paramsObj = rinseParams(method.getParameters(), params);
                return method.invoke(bean, paramsObj.toArray());
            }
            throw new NoSuchMethodException("没有找到：" + methodName + " 这样的方法");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return IoUtils.throwableErrToStr(e);
        }
    }

    /**
     * 清洗入参。
     *
     * @return
     */
    private List<Object> rinseParams(Parameter[] params, List<String> paramVals) {
        List<Object> newParamsVals = new ArrayList<>();
        int len = paramVals.size();
        for (int i = 0; i < len; i++) {
            String parasS = paramVals.get(i);
            Class<?> type = params[i].getType();
            Object paramObj = StrUtils.changeTo(parasS, type);
            if (Objects.isNull(paramObj)) {
                paramObj = matcheParamsObj(parasS, type);
            }
            newParamsVals.add(paramObj);
        }
        return newParamsVals;
    }

    /**
     * #{new xxx{}} 格式 或json 格式
     */
    private Object matcheParamsObj(String parasS, Class<?> type) {
        if (parasS.matches("^#\\{new .*?\\(\\)}$")) { // 以 #{new xxxx()}的格式,一般代表无构造参数
            try {
                return type.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {
            }
        }
        try {
            return StrUtils.json().fromJson(parasS, type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
}
