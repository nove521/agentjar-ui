package com.cx.javaCompiler;

/**
 * 用于查找classload路径的工具类
 */
public class FindClassLoadPackage {

    private ClassLoader classLoader;

    public FindClassLoadPackage(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
