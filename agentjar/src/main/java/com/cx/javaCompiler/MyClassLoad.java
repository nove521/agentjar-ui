package com.cx.javaCompiler;

import java.util.Map;
import java.util.Objects;

public class MyClassLoad extends ClassLoader {

    private final Map<String, ClassByteSource> classByteSourceMap;

    public MyClassLoad(ClassLoader parent, Map<String, ClassByteSource> classByteSourceMap) {
        super(parent);
        this.classByteSourceMap = classByteSourceMap;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ClassByteSource classByteSource = this.classByteSourceMap.get(name);
        if (Objects.isNull(classByteSource)) {
            return super.findClass(name);
        }
        byte[] bytes = classByteSource.getByteSource();
        return super.defineClass(name, bytes, 0, bytes.length);
    }

}
