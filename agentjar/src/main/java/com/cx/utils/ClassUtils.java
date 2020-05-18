package com.cx.utils;

import com.cx.agent.Session;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Objects;

public class ClassUtils {

    /**
     * 翻译Modifier值
     *
     * @param mod modifier
     * @return 翻译值
     */
    public static String modifier(int mod, char splitter) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isAbstract(mod)) {
            sb.append("abstract").append(splitter);
        }
        if (Modifier.isFinal(mod)) {
            sb.append("final").append(splitter);
        }
        if (Modifier.isInterface(mod)) {
            sb.append("interface").append(splitter);
        }
        if (Modifier.isNative(mod)) {
            sb.append("native").append(splitter);
        }
        if (Modifier.isPrivate(mod)) {
            sb.append("private").append(splitter);
        }
        if (Modifier.isProtected(mod)) {
            sb.append("protected").append(splitter);
        }
        if (Modifier.isPublic(mod)) {
            sb.append("public").append(splitter);
        }
        if (Modifier.isStatic(mod)) {
            sb.append("static").append(splitter);
        }
        if (Modifier.isStrict(mod)) {
            sb.append("strict").append(splitter);
        }
        if (Modifier.isSynchronized(mod)) {
            sb.append("synchronized").append(splitter);
        }
        if (Modifier.isTransient(mod)) {
            sb.append("transient").append(splitter);
        }
        if (Modifier.isVolatile(mod)) {
            sb.append("volatile").append(splitter);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 翻译类名称<br/>
     * 将 java/lang/String 的名称翻译成 java.lang.String
     *
     * @param className 类名称 java/lang/String
     * @return 翻译后名称 java.lang.String
     */
    public static String normalizeClassName(String className) {
        return StrUtils.replace(className, "/", ".");
    }

    public static String concat(String seperator, Class<?>... types) {
        if (types == null || types.length == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            builder.append(classname(types[i]));
            if (i < types.length - 1) {
                builder.append(seperator);
            }
        }

        return builder.toString();
    }

    /**
     * 翻译类名称
     *
     * @param clazz Java类
     * @return 翻译值
     */
    public static String classname(Class<?> clazz) {
        if (clazz.isArray()) {
            StringBuilder sb = new StringBuilder(clazz.getName());
            sb.delete(0, 2);
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ';') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("[]");
            return sb.toString();
        } else {
            return clazz.getName();
        }
    }

    public static ClassLoader generateClassLoad(Class<?> clazz) {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        return new URLClassLoader(new URL[]{url}, clazz.getClassLoader().getParent());
    }

    /**
     * 获取class类文件路径 如果是jar包则直接返回
     *
     * @param classes
     * @param className
     * @return
     */
    public static String getPathByClassName(List<Class<?>> classes, String className) {
        Class<?> objClazz = null;
        for (Class<?> clazz : classes) {
            if (clazz.getName().equals(className)) {
                objClazz = clazz;
                break;
            }
        }

        if (Objects.isNull(objClazz)) {
            return null;
        }

        className = className.replace(".", "/");
        URL resource = objClazz.getClassLoader().getResource(className + ".class");

        if (Objects.isNull(resource)) {
            return null;
        }

        if (resource.toString().indexOf(".jar") > 0) {
            return className;
        }

        try {
            return new File(resource.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getByteByClass(Class<?> clazz) {
        InputStream stream = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace(".", "/") + ".class");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/59780/Desktop/by/xx/111/cccc.class");
            IoUtils.inputStreamToOutputStream(stream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        getByteByClass(ClassUtils.class);
    }
}

