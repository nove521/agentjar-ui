package com.cx.javaCompiler;

import com.cx.enums.SystemMessage;
import com.cx.utils.ClassUtils;

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
    // 我的文件管理系统
    private MyJavaFileManage myJavaFileManage;

    public MyCompiler(ClassLoader classLoader) {
        if (Objects.isNull(classLoader)) {
            this.classLoader = ClassUtils.generateClassLoad(this.getClass());
        } else {
            this.classLoader = classLoader;
        }
        options.add("-Xlint:unchecked"); // 取消编译警告
    }

    /**
     * 执行单个文件编译
     *
     * @param name       类名包含路径
     * @param javaString java源代码
     * @return 编译好的class字节码 ,之所以是map，是因为 可能存在内部类，内部类，会单独建一个class
     */
    public Map<String, byte[]> start(String name, String javaString) {
        addInputJavaFileObject(name, javaString);
        Map<String, byte[]> byteMap = start();
        return byteMap == null ? Collections.emptyMap() : byteMap;
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
        MyJavaFileManage myJavaFileManage = new MyJavaFileManage(javaFileManager, classLoader);
        this.myJavaFileManage = myJavaFileManage;
        JavaCompiler.CompilationTask task = javaCompiler.getTask(
                null,
                myJavaFileManage,
                diagnostics,
                options,
                null,
                inputJavaFileObjects);

        Boolean result = task.call();
        if (result) {
            return myJavaFileManage.getOutClassSource();
        } else {
            MyExceptionReporter reporter = new MyExceptionReporter(diagnostics.getDiagnostics());
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

    public void addInputJavaFileObject(String name, String javaString) {
        JavaSourceFromString sourceFromString = new JavaSourceFromString(name, javaString);
        addInputJavaFileObject(sourceFromString);
    }

    public void addInputJavaFileObject(JavaFileObject javaFileObject) {
        inputJavaFileObjects.add(javaFileObject);
    }

}
