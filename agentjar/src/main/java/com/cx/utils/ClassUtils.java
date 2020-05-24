package com.cx.utils;

import com.cx.mode.MethodInfo;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
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

        if (className.contains("$")) {
            className = className.substring(0, className.indexOf("$"));
        }

        className = className.replace(".", "/");

        URL resource = objClazz.getClassLoader().getResource(className + ".class");

        if (Objects.isNull(resource)) {
            return null;
        }

        String path = resource.toString().replaceAll("file:", "");
        return path.replaceAll("jar:","");
    }

    public static byte[] getByteByClass(Class<?> clazz) {
        try {
            InputStream stream = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace(".", "/") + ".class");
            if (Objects.isNull(stream)) {
                return null;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IoUtils.inputStreamToOutputStream(stream, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MethodInfo> getMethodsInfo(Class<?> clazz) {
        List<MethodInfo> list = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            MethodInfo info = new MethodInfo(method);
            list.add(info);
        }
        return list;
    }

    public static String getSimplePath(String name){
        int index = name.indexOf("$");
        String path = name.substring(0, index < 0 ? name.length() - 1 : index);
        path = path.replace(".","/");
        return path + ".class";
    }

    public static void main(String[] args) {
        URL resource = ClassUtils.class.getClassLoader().getResource("app.properties");
        System.out.println(resource.getFile());
        System.out.println(ClassUtils.class.getProtectionDomain().getCodeSource().getLocation());

        String s = "file:/D:/project/xuexi/TestHotUpdate/clientweb/target/classes/agentjar-1.0.jar!/org/yx/http/kit/HttpTypePredicate.class";
        System.out.println(getSimplePath("java.util.function.Predicate$$Lambda$460/474709547"));
        System.out.println(s.replaceAll("file:",""));
    }
}

