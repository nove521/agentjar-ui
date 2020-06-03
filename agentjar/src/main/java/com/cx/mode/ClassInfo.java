package com.cx.mode;

import com.cx.utils.BeanU;

import java.util.Map;
import java.util.Objects;

/**
 * 类的基本信息
 */
public class ClassInfo {

    /**
     * 类路径
     */
    private String name;

    /**
     * 类名
     */
    private String SimpleName;

    /**
     * 源码路径
     */
    private String codeSource;

    /**
     * 是接口？
     */
    private boolean isInterface;

    /**
     * 是注解？
     */
    private boolean isAnnotation;

    /**
     * 是枚举？
     */
    private boolean isEnum;

    /**
     * 是匿名类？
     */
    private boolean isAnonymousClass;

    /**
     * 是数组？
     */
    private boolean isArray;

    /**
     * 是局部类？
     */
    private boolean isLocalClass;

    /**
     * modifier
     */
    private String modifier;

    /**
     * 注解
     */
    private String annotation;

    /**
     * 接口
     */
    private String interfaces;

    /**
     * 父类
     */
    private String superClass;

    /**
     * class-loader
     */
    private String ClassLoad;

    /**
     * ClassLoad地址
     */
    private String classLoaderHash;

    public ClassInfo(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return;
        }
        initInfo(clazz);
    }

    private void initInfo(Class<?> clazz) {
        ClassLoader loader = clazz.getClassLoader();
        if (Objects.nonNull(loader)) {
            setClassLoad(loader.toString());
            setClassLoaderHash(Integer.toHexString(loader.hashCode()));
        }
        setAnnotation(clazz.isAnnotation());
        setAnonymousClass(clazz.isAnonymousClass());
        setArray(clazz.isArray());
        setEnum(clazz.isEnum());
        setInterface(clazz.isInterface());
        setLocalClass(clazz.isLocalClass());
        setName(clazz.getName());
        setSimpleName(clazz.getSimpleName());
        setSuperClass(Objects.nonNull(clazz.getSuperclass()) ? clazz.getSuperclass().getName() : "无");
    }

    public Map<String, Object> toMap() throws IllegalAccessException {
        return BeanU.beanToMap(this, false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return SimpleName;
    }

    public void setSimpleName(String simpleName) {
        SimpleName = simpleName;
    }

    public String getCodeSource() {
        return codeSource;
    }

    public void setCodeSource(String codeSource) {
        this.codeSource = codeSource;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public boolean isAnnotation() {
        return isAnnotation;
    }

    public void setAnnotation(boolean annotation) {
        isAnnotation = annotation;
    }

    public boolean isEnum() {
        return isEnum;
    }

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }

    public boolean isAnonymousClass() {
        return isAnonymousClass;
    }

    public void setAnonymousClass(boolean anonymousClass) {
        isAnonymousClass = anonymousClass;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isLocalClass() {
        return isLocalClass;
    }

    public void setLocalClass(boolean localClass) {
        isLocalClass = localClass;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getClassLoad() {
        return ClassLoad;
    }

    public void setClassLoad(String classLoad) {
        ClassLoad = classLoad;
    }

    public String getClassLoaderHash() {
        return classLoaderHash;
    }

    public void setClassLoaderHash(String classLoaderHash) {
        this.classLoaderHash = classLoaderHash;
    }
}
