package com.cx.javaCompiler2;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

public class MyJavaFileManage extends ForwardingJavaFileManager {

    private ClassByteSource classByteSource;

    protected MyJavaFileManage(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        classByteSource = new ClassByteSource(className);
        return classByteSource;
    }

    public ClassByteSource getOutJavaFile(){
        return classByteSource;
    }

}
