package com.cx.utils;

import com.cx.enums.StatusSys;
import com.cx.mode.MethodInfo;
import com.cx.server.ann.Http;
import com.cx.server.ann.Join;
import com.cx.server.ann.Obj;
import javassist.NotFoundException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.NotBoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassUtils {

    public static final String CLASS_SUFFIX = ".class";

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

        URL resource = objClazz.getClassLoader().getResource(className + CLASS_SUFFIX);
        if (Objects.isNull(resource)) {
            return null;
        }
        String path = resource.toString().replaceAll(FileUtils.FILE_PREFIX, "");
        return path.replaceAll(JarTool.JAR_PREFIX, "");
    }

    public static byte[] getByteByClass(Class<?> clazz) {
        try {
            InputStream stream = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace(".", "/") + CLASS_SUFFIX);
            if (Objects.isNull(stream)) {
                return null;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IoUtils.inputStreamToOutputStream(stream, outputStream, true);
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

    public static String getSimplePath(String name) {
        int index = name.indexOf("$");
        String path = name.substring(0, index < 0 ? name.length() : index);
        path = path.replace(".", "/");
        return path + CLASS_SUFFIX;
    }

    public static List<Class<?>> getClassByPackNames(List<String> packNames) throws ClassNotFoundException {
        List<Class<?>> reuslt = new ArrayList<>();
        for (String packName : packNames) {
            reuslt.add(Class.forName(packName));
        }
        return reuslt;
    }

    public static List<Class<?>> filterClass(List<Class<?>> classList, Predicate<Class<?>> predicate) {
        return classList.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 过滤
     *
     * @param classList
     * @param anns
     * @return
     */
    public static List<Class<?>> filterClassByAnn(List<Class<?>> classList, Class<?>... anns) {
        List<Class<?>> asList = Arrays.asList(anns);

        return classList.stream().filter(clazz -> {
            List<Annotation> annotations = Arrays.asList(clazz.getAnnotations());
            return annotations.stream().anyMatch(item -> asList.contains(item.annotationType()));
        }).collect(Collectors.toList());
    }

    /**
     * 生成 bean
     *
     * @param list
     * @param beans
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static Map<Class<?>, Object> generateBeans(List<Class<?>> list, Map<Class<?>, Object> beans) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for (Class<?> aClass : list) {
            Object obj = aClass.newInstance();
            beans.put(aClass, obj);
        }
        linkBeans(beans);
        return beans;
    }

    /**
     * 链接
     *
     * @param beanMap
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    private static Map<Class<?>, Object> linkBeans(Map<Class<?>, Object> beanMap) throws ClassNotFoundException, IllegalAccessException {
        for (Class<?> aClass : beanMap.keySet()) {
            Object currObj = beanMap.get(aClass);
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                Object bean = getFieldAnnBean(field, beanMap);
                if (Objects.nonNull(bean)) {
                    field.setAccessible(true);
                    field.set(currObj, bean);
                }
            }
        }
        return beanMap;
    }

    /**
     * 获取字段bean对象
     *
     * @param field
     * @param beanMap
     * @return
     * @throws ClassNotFoundException
     */
    private static Object getFieldAnnBean(Field field, Map<Class<?>, Object> beanMap) throws ClassNotFoundException {
        List<Annotation> annotations = Arrays.asList(field.getAnnotations());
        boolean match = annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(Join.class));
        if (match) {
            Class<?> type = field.getType();
            Object bean = beanMap.get(type);
            if (Objects.isNull(bean)) {
                throw new ClassNotFoundException("没有找到" + field.getName());
            }
            return bean;
        }
        return null;
    }

    public static boolean equalsAnn(Annotation[] annotation, Class<?> clazz) {
        List<Annotation> annotationList = Arrays.asList(annotation);
        return annotationList.stream().anyMatch(ann -> ann.annotationType().equals(clazz));
    }

    public static Annotation equalsGetAnn(Annotation[] annotation, Class<?> clazz) {
        List<Annotation> annotationList = Arrays.asList(annotation);
        Optional<Annotation> first = annotationList.stream().filter(ann -> ann.annotationType().equals(clazz)).findFirst();
        return first.orElse(null);
    }

    public static String getAnnsVal(Annotation[] annotation) {
        List<Annotation> annotationList = Arrays.asList(annotation);
        Optional<Annotation> first = annotationList.stream().filter(annotation1 -> annotation1.annotationType().equals(Http.class)).findFirst();
        Annotation annotation1 = first.get();
        Http http = (Http) annotation1;
        return http.value();
    }

    public static Method matchMethod(Method[] methods, String methodName) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> getDirMapDataByUrl(URL url) throws URISyntaxException {
        String path = url.toString();
        if (path.matches(JarTool.IS_JAR_REX)) {
            List<String> pathByJar = FileUtils.getDirAllResourceByJar(url.toURI());
            return generateDirDate(pathByJar);
        } else {
            File file = new File(url.toURI());
            List<String> pathList = new ArrayList<>();
            FileUtils.getFiles(file, pathList);

            String finalPath = path.replace("file:/", "");

            List<String> collect = pathList.stream().map(item -> item.replace(finalPath, "")).collect(Collectors.toList());
            return generateDirDate(collect);
        }
    }

    public static String getUrlFileCode(URL url, String fileName) throws URISyntaxException, IOException {
        String path = url.toString();
        if (path.matches(JarTool.IS_JAR_REX)) {
            return FileUtils.getJarFileCode(url.toURI(), fileName);
        } else {
            return FileUtils.getFileCode(url.toURI(), fileName);
        }
    }

    public static StatusSys editUrlFileCode(URL url, String fileName, String code, boolean isBak) throws URISyntaxException {
        String path = url.toString();
        if (path.matches(JarTool.IS_JAR_REX)) {
            return FileUtils.editJarFileCode(url.toURI(), fileName, code);
        } else {
            return FileUtils.editFileCode(url.toURI(), fileName, code, isBak);
        }
    }

    /**
     * @param filesList
     * @return
     */
    public static List<Map<String, Object>> generateDirDate(List<String> filesList) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> cache = new HashMap<>();
        int id = 1;

        for (String fileName : filesList) {
            String[] split = fileName.split("/");
            StringBuilder key = new StringBuilder();
            List<Map<String, Object>> resultList = result;
            for (String item : split) {
                key.append("/").append(item);
                Object val = cache.get(key.toString());
                if (Objects.isNull(val)) {
                    Map<String, Object> children = new HashMap<>();
                    children.put("id", id++);
                    children.put("label", item);
                    children.put("path", key.substring(1));
                    children.put("isFile", fileName.endsWith(item));
                    children.put("children", new ArrayList<>());
                    resultList.add(children);
                    cache.put(key.toString(), children);
                    resultList = (List<Map<String, Object>>) children.get("children");
                } else {
                    resultList = (List<Map<String, Object>>) ((Map) val).get("children");
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        URL resource = ClassUtils.class.getClassLoader().getResource("app.properties");
//        System.out.println(resource.getFile());
//        System.out.println(ClassUtils.class.getProtectionDomain().getCodeSource().getLocation());
//
//        String s = "file:/D:/project/xuexi/TestHotUpdate/clientweb/target/classes/agentjar-1.0.jar!/org/yx/http/kit/HttpTypePredicate.class";
//        System.out.println(getSimplePath("java.util.function.Predicate$$Lambda$460/474709547"));
//        System.out.println(s.replaceAll("file:", ""));

        // BOOT-INF/classes/com/example/simple/SimpleApplication.class, BOOT-INF/classes/templates/sds]
        List<String> list = new ArrayList<>();
//        list.add("BOOT-INF/classes/");
//        list.add("BOOT-INF/classes/com/");
//        list.add("BOOT-INF/classes/com/example/");
//        list.add("BOOT-INF/classes/com/example/simple/");
//        list.add("BOOT-INF/classes/com/example/simple/controller/");
//        list.add("BOOT-INF/classes/com/example/simple/controller/");
//        list.add("BOOT-INF/classes/templates/");
//        list.add("BOOT-INF/classes/application.properties");
//        list.add("BOOT-INF/classes/com/example/simple/controller/HelloController.class");
//        list.add("BOOT-INF/classes/com/example/simple/controller/X.class");
//        list.add("BOOT-INF/classes/com/example/simple/SimpleApplication.class");
//        list.add("BOOT-INF/classes/templates/sds");
        // com/example/simple/controller/X.class, com/example/simple/SimpleApplication.class, templates/sds]
        list.add("application.properties");
        list.add("com/example/simple/controller/X.class");
        list.add("com/example/simple/controller/HelloController.class");
        list.add("templates/sds");
        list.add("com/example/simple/SimpleApplication.class");
        List<Map<String, Object>> list1 = generateDirDate(list);
        System.out.println(StrUtils.json().toJson(list1));
    }

}

