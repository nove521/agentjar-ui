package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.javaCompiler.MyCompiler;
import org.yx.annotation.Bean;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Objects;

@Bean
public class HotUpdateService {

    private static final Instrumentation INS = Session.getIns();

    public String updateClassByString(String className, String javaCode) {
        Class<?> clazz = Session.getClassCache(className);
        if (Objects.isNull(clazz)){
            return "找不到类";
        }

        MyCompiler compiler = new MyCompiler(clazz.getClassLoader());

        byte[] clazzByte = compiler.start(className, javaCode);

        if (clazzByte.length > 0){
            ClassDefinition classDefinition = new ClassDefinition(clazz,clazzByte);
            try {
                INS.redefineClasses(classDefinition);
            } catch (ClassNotFoundException | UnmodifiableClassException e) {
                e.printStackTrace();
            }
        }else {
            return "编译失败";
        }
        return "ok";
    }

}
