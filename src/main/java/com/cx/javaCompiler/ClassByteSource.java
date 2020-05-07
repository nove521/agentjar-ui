package com.cx.javaCompiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * 自定义 java文件对象。用于获取一个输出流。返回字节。
 * 用于输出
 */
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
