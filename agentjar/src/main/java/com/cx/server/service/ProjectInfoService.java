package com.cx.server.service;

import com.cx.agent.ContextManage;
import com.cx.agent.Session;
import com.cx.contentBean.BeanManage;
import com.cx.contentBean.factory.GetBeanFactory;
import com.cx.javaCompiler.Decompiler;
import com.cx.mode.ClassInfo;
import com.cx.mode.MethodInfo;
import com.cx.server.ann.Join;
import com.cx.server.ann.Obj;
import com.cx.utils.ClassUtils;
import com.cx.utils.StrUtils;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目相关服务工具
 */
@Obj
public class ProjectInfoService {

    private static final Instrumentation INS = Session.getIns();

    @Join
    private HotUpdateService hotUpdateService;

    /**
     * 获取jvm加载的所有类
     *
     * @return <类名,类路径>
     */
    public Map<String, Object> getAllClass(int page, int size, String pattenName) {
        pattenName = StrUtils.isBlank(pattenName) ? "" : pattenName;
        String regx = "^.*" + pattenName + ".*$";

        List<Class<?>> classList = Session.getClassCache();
        int total = (int) classList.stream().filter(item -> item.getName().matches(regx)).count();
        List<Map<String, Object>> data = classList.stream()
                .filter(item -> item.getName().matches(regx))
                .skip((page - 1) * size)
                .limit(size)
                .map(clazz -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("SimpleName", "".equals(clazz.getSimpleName()) ? clazz.getName() : clazz.getSimpleName());
                    result.put("name", clazz.getName());
                    result.put("info", new ClassInfo(clazz));
                    result.put("methods", ClassUtils.getMethodsInfo(clazz));
                    BeanManage beanManage = GetBeanFactory.generateBeanManage();
                    if (Objects.isNull(beanManage)) {
                        result.put("haveBean", true);
                    } else {
                        Object bean = beanManage.getBean(clazz);
                        result.put("haveBean", Objects.nonNull(bean));
                    }
                    return result;
                }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("list", data);
        return result;
    }

    /**
     * 获取指定类的信息
     *
     * @param className 类的名字
     * @return classInfo
     */
    public ClassInfo getClassInfo(String className) {
        return new ClassInfo(getClazz(className));
    }

    public Map<String, String> getClassCodeSource(String className) {
        Map<String, String> result = new HashMap<>();
        String javaCode = Session.getCacheNewClassByte().get(className);

        if (Objects.isNull(javaCode)) {
            String path = ClassUtils.getPathByClassName(Session.getClassCache(), className);
            javaCode = Decompiler.decompile(path, null);
            if (StrUtils.isBlank(javaCode)) {
                result.put("code", "not Find");
                result.put("name", className);
                return result;
            }
        }

        result.put("code", javaCode);
        result.put("name", className);
        return result;
    }

    public List<MethodInfo> getClassAllMethods(String className) {
        Class<?> clazz = getClazz(className);
        return ClassUtils.getMethodsInfo(clazz);
    }



    private Class<?> getClazz(String className) {
        List<Class<?>> classList = Session.getClassCache();
        Optional<Class<?>> aClass = classList.stream().filter(item -> item.getName().equals(className)).findFirst();
        return aClass.orElse(null);
    }


    public void stop(String restore) {
        boolean restoreBool = Boolean.parseBoolean(restore);
        if (restoreBool) {
            hotUpdateService.resetClass();
        }
        ContextManage.stop();
    }

}
