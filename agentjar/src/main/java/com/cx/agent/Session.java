package com.cx.agent;

import com.cx.enums.SystemMessage;

import java.lang.instrument.Instrumentation;
import java.util.*;

public class Session {

    private static Instrumentation ins;
    public final static List<Class<?>> allClassCache = new ArrayList<>();

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
        if (allClassCache.isEmpty()) {
            Class<?>[] allLoadedClasses = ins.getAllLoadedClasses();
            List<Class<?>> classes = Arrays.asList(allLoadedClasses);
            allClassCache.addAll(classes);
        }
        return allClassCache;
    }

    public static Class<?> getClassCache(String className) {
        Optional<Class<?>> classOptional = getClassCache().stream().filter(item -> item.getName().equals(className)).findFirst();
        return classOptional.orElse(null);
    }

    public static void updateCache(){
        allClassCache.clear();
        getClassCache();
    }
}
