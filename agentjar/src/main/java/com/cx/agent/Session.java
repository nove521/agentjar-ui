package com.cx.agent;

import com.cx.enums.SystemMessage;

import java.lang.instrument.Instrumentation;
import java.util.*;
import java.util.stream.Collectors;

public class Session {

    private static Instrumentation ins;

    // 保留热更新前 原始的类文件
    private static final Map<Class<?>,byte[]> cacheClassByte = new HashMap<>();
    // 存放最新更改的java文件。
    private static final Map<String,String> cacheNewClassByte = new HashMap<>();

    public static void setIns(Instrumentation ins) {
        Session.ins = ins;
    }

    public static Instrumentation getIns() {

        if (Objects.isNull(Session.ins)) {
            throw new NullPointerException(SystemMessage.UN_INIT_INS.getVal());
        }
        return Session.ins;
    }

    public static boolean isHaveIns() {
        return Objects.nonNull(getIns());
    }

    public static String info() {
        Class<?>[] classes = getIns().getAllLoadedClasses();
        List<Class<?>> classList = Arrays.asList(classes);
        StringBuilder sb = new StringBuilder();
        classList.forEach(e -> {
            sb.append(e.getName());
        });
        return sb.toString();
    }

    public static List<Class<?>> getClassCache() {
        Class<?>[] allLoadedClasses = ins.getAllLoadedClasses();
        return Arrays.stream(allLoadedClasses).filter(item ->{
            if (Objects.isNull(item.getClassLoader())){
                return false;
            }
            try {
                if (item.getProtectionDomain().getCodeSource().getLocation().toString().contains("agentjar-")){
                    return false;
                }
            }catch (NullPointerException e){
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    public static Class<?> getClassCache(String className) {
        Optional<Class<?>> classOptional = getClassCache().stream().filter(item -> item.getName().equals(className)).findFirst();
        return classOptional.orElse(null);
    }

    public static void updateCache() {
        getClassCache();
    }

    public static Map<Class<?>,byte[]> getCacheClassByte(){
        return Session.cacheClassByte;
    }

    public static Map<String,String> getCacheNewClassByte(){
        return Session.cacheNewClassByte;
    }

    public static void clearCacheClassByte(){
        Session.cacheClassByte.clear();
    }

    public static void clearCacheNewClassByte(){
        Session.cacheNewClassByte.clear();
    }
}
