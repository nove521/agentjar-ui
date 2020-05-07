package com.cx.javaCompiler;

import com.cx.javaCompiler2.ClassByteSource;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ForwardingJavaFileManager 这个类其实就是帮你内部做了一些代码。 目的只是为 标准化文件管理 做一层包装。 提供给外部自定义接口方便继承。
 * 就可以不需要全部实现 接口。自己写比较麻烦。 可针对化的进行 定制。前提是要了解到每个方法的意思
 *
 * 自定义的文件管理
 */
public class MyJavaFileManage extends ForwardingJavaFileManager {

    // 存放 编译好的class文件字节码
    private Map<String, ClassByteSource> classByteSourceMap;

    protected MyJavaFileManage(JavaFileManager fileManager) {
        super(fileManager);
        classByteSourceMap = new HashMap<>();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ClassByteSource classByteSource = new ClassByteSource(className);
        // TODO 这里名字可能不正确
        classByteSourceMap.put(className, classByteSource);
        return classByteSource;
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
