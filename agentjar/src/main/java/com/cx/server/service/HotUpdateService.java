package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.javaCompiler.MyCompiler;
import com.cx.utils.ClassUtils;
import com.cx.utils.IoUtils;
import org.yx.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
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
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    /**
     * 保存原始class文件
     */
    private void cacheOldClassResource(Class<?> clazz) {
        Map<Class<?>, byte[]> cacheClassByte = Session.getCacheClassByte();
        if (Objects.isNull(cacheClassByte.get(clazz))) {
            byte[] bytes = ClassUtils.getByteByClass(clazz);
            cacheClassByte.put(clazz, bytes);
        }
    }

}
