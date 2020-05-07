package com.cx.javaCompiler2;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ClassByteSource extends SimpleJavaFileObject {

    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    ByteArrayOutputStream byteArrayOutputStream;

    public ClassByteSource(String className) {
        super(URI.create("byte:///" + className.replace(PKG_SEPARATOR, DIR_SEPARATOR)
                + Kind.CLASS.extension), Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        if (byteArrayOutputStream == null){
            byteArrayOutputStream = new ByteArrayOutputStream();
        }
        return byteArrayOutputStream;
    }

    public byte[] getByteSource(){
        if (byteArrayOutputStream!=null){
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }
}
