package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.mode.ClassInfo;
import org.yx.annotation.Bean;

import java.lang.instrument.Instrumentation;
import java.util.*;

/**
 * 项目相关服务工具
 */
@Bean
public class ProjectInfoService {

    private static final Instrumentation INS = Session.getIns();

    /**
     * 获取jvm加载的所有类
     *
     * @return <类名,类路径>
     */
    public Map<String, String> getAllClass(int page, int size) {

        List<Class<?>> classList = Session.getClassCache();
        Map<String, String> result = new HashMap<>();
        classList.stream()
                .filter(item -> item.getName().startsWith("comxxx."))
                .skip((page - 1) * size)
                .limit(size)
                .forEach(clazz -> result.put("".equals(clazz.getSimpleName()) ? clazz.getName() : clazz.getSimpleName(), clazz.getName()));
        return result;
    }

    /**
     * 获取指定类的信息
     * @param className 类的名字
     * @return classInfo
     */
    public ClassInfo getClassInfo(String className){
        List<Class<?>> classList = Session.getClassCache();
        Optional<Class<?>> aClass = classList.stream().filter(item -> item.getName().equals(className)).findFirst();
        return new ClassInfo(aClass.get());
    }

}
