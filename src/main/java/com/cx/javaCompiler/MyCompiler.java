package com.cx.javaCompiler;

import com.cx.enums.SystemMessage;

import javax.tools.*;
import java.util.*;

/**
 * 动态编译主入口
 */
public class MyCompiler {

    /**
     * 主要作用有 2 个
     * 1. 帮助找到依赖
     * 2. 可以执行编译好的class，可作用于反射调用方法
     */
    private final ClassLoader classLoader;
    // java 动态编译主要类
    private final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    // 编译时的配置
    private final Collection<String> options = new ArrayList<>();
    // 输入的java对象文件 集合
    private final Collection<JavaFileObject> inputJavaFileObjects = new ArrayList<>();

    public MyCompiler(ClassLoader classLoader) {
        this.classLoader = classLoader;
        options.add("-Xlint:unchecked"); // 取消编译警告
    }

    /**
     * 执行单个文件编译
     *
     * @param name       类名包含路径
     * @param javaString java源代码
     * @return 编译好的class字节码
     */
    public byte[] start(String name, String javaString) {
        addInputJavaFileObject(name,javaString);
        return start().get(name);
    }

    /**
     * 批量编译
     *
     * @return 编译好的class字节码 集合
     */
    public Map<String, byte[]> start() {
        // 保存编译异常的信息
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileManager javaFileManager = javaCompiler.getStandardFileManager(diagnostics, null, null);
        MyJavaFileManage myJavaFileManage = new MyJavaFileManage(javaFileManager);

        JavaCompiler.CompilationTask task = javaCompiler.getTask(
                null,
                myJavaFileManage,
                diagnostics,
                options,
                null,
                inputJavaFileObjects);

        Boolean result = task.call();
        if (result){
            return myJavaFileManage.getOutClassSource();
        }
        return null;
    }

    /**
     * 执行单个文件编译
     *
     * @param javaFileObject java文件对象
     * @return 编译好的class字节码
     */
    public byte[] start(JavaFileObject javaFileObject) {
        throw new UnsupportedOperationException(SystemMessage.UN_OPERATION.getVal());
    }

    public void addInputJavaFileObject(String name, String javaString){
        JavaSourceFromString sourceFromString = new JavaSourceFromString(name, javaString);
        addInputJavaFileObject(sourceFromString);
    }

    public void addInputJavaFileObject(JavaFileObject javaFileObject){
        inputJavaFileObjects.add(javaFileObject);
    }
}
