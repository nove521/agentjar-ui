package com.cx.javaCompiler;

import com.cx.enums.SystemMessage;

import javax.tools.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

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
        if (Objects.isNull(classLoader)) {
            this.classLoader = this.getClass().getClassLoader();
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
     * @return 编译好的class字节码
     */
    public byte[] start(String name, String javaString) {
        addInputJavaFileObject(name, javaString);
        Map<String, byte[]> byteMap = start();
        return byteMap == null ? new byte[0] : byteMap.get(name);
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
        MyJavaFileManage myJavaFileManage = new MyJavaFileManage(javaFileManager,classLoader);

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
        }else {
            MyExceptionReporter reporter = new MyExceptionReporter(diagnostics.getDiagnostics());
            System.out.println(reporter.toReporter());
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
