package com.cx.javaCompiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ForwardingJavaFileManager 这个类其实就是帮你内部做了一些代码。 目的只是为 标准化文件管理 做一层包装。 提供给外部自定义接口方便继承。
 * 就可以不需要全部实现 接口。自己写比较麻烦。 可针对化的进行 定制。前提是要了解到每个方法的意思
 * <p>
 * 自定义的文件管理
 */
public class MyJavaFileManage extends ForwardingJavaFileManager {

    // 存放 编译好的class文件字节码
    private final Map<String, ClassByteSource> classByteSourceMap = new HashMap<>();
    // 用于从指定classLoad路径中查找类
    private final MyClassLoad classLoader;
    private FindClassLoadPackage find;

    public MyJavaFileManage(JavaFileManager fileManager, ClassLoader classLoader) {
        super(fileManager);
        this.classLoader = new MyClassLoad(classLoader,classByteSourceMap);
        this.find = new FindClassLoadPackage(classLoader);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ClassByteSource classByteSource = new ClassByteSource(className);
        // TODO 这里名字可能不正确
        classByteSourceMap.put(className, classByteSource);
        return classByteSource;
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return this.classLoader;
    }

    @Override
    public Iterable<JavaFileObject> list(Location location, String packageName, Set kinds, boolean recurse) throws IOException {
        return super.list(location, packageName, kinds, recurse);
    }

    public Map<String, ClassByteSource> getOutJavaFiles() {
        return classByteSourceMap;
    }

    public Map<String, byte[]> getOutClassSource() {
        Map<String, byte[]> result = new HashMap<>();
        for (Map.Entry<String, ClassByteSource> entry : classByteSourceMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getByteSource());
        }
        return result;
    }

}
