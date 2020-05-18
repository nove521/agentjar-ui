package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.javaCompiler.Decompiler;
import com.cx.mode.ClassInfo;
import com.cx.utils.ClassUtils;
import com.cx.utils.StrUtils;
import org.yx.annotation.Bean;
import org.yx.main.SumkServer;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.System.arraycopy;

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
    public Map<String, Object> getAllClass(int page, int size, String pattenName) {
        pattenName = StrUtils.isBlank(pattenName) ? "" : pattenName;
        String regx = "^.*" + pattenName + ".*$";

        List<Class<?>> classList = Session.getClassCache();
        int total = (int) classList.stream().filter(item -> item.getName().matches(regx)).count();
        List<Map<String, String>> data = classList.stream()
                .filter(item -> item.getName().matches(regx))
                .skip((page - 1) * size)
                .limit(size)
                .map(clazz -> {
                    Map<String, String> result = new HashMap<>();
                    result.put("SimpleName", "".equals(clazz.getSimpleName()) ? clazz.getName() : clazz.getSimpleName());
                    result.put("name", clazz.getName());
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
        List<Class<?>> classList = Session.getClassCache();
        Optional<Class<?>> aClass = classList.stream().filter(item -> item.getName().equals(className)).findFirst();
        return new ClassInfo(aClass.get());
    }

    public Map<String, String> getClassCodeSource(String className) {
        Map<String, String> result = new HashMap<>();
        String path = ClassUtils.getPathByClassName(Session.getClassCache(), className);
        if (Objects.isNull(path)) {
            result.put("code", "not Find");
            result.put("name", className);
            return result;
        }
        String code = Decompiler.decompile(path, null);
        result.put("code", code);
        result.put("name", className);
        return result;
    }

    public void stop() {
        resetClass();
        SumkServer.stop();
    }

    private void resetClass() {
        Map<Class<?>, byte[]> cacheClassByte = Session.getCacheClassByte();

        Set<Class<?>> keySet = cacheClassByte.keySet();
        if (keySet.isEmpty()) {
            System.out.println("keySet is null");
            return;
        }
        // 一个空的转换器
        final ClassFileTransformer resetClassTransformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader,
                                    String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) throws IllegalClassFormatException {
                byte[] bytes = cacheClassByte.get(classBeingRedefined);
                System.out.println("bytes "+ bytes.length);
                return bytes;
            }
        };
        INS.addTransformer(resetClassTransformer, true);

        Class<?>[] array = keySet.toArray(new Class<?>[0]);

        try {
            if (array.length > 0) {
                System.out.println("arr len " + array[0].getName());
                INS.retransformClasses(array);
            }
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        } finally {
            INS.removeTransformer(resetClassTransformer);
            Session.clearCacheClassByte();
        }
    }

}
