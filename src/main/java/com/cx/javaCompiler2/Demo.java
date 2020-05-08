package com.cx.javaCompiler2;

import com.cx.javaCompiler.JavaSourceFromString;
import com.cx.javaCompiler.MyJavaFileManage;
import com.cx.utils.IoUtils;

import javax.tools.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Demo {
    public static void main(String[] args) throws IOException {
        File[] files1 = new File[]{new File("D:/project/学习/TestHotUpdate/src/main/java/com/cx/Test.java")}; // input for first compilation task
        File[] files2 = new File[]{new File("D:/project/学习/TestHotUpdate/src/main/java/com/cx/Sss.java")}; // input for second compilation task

        String source = IoUtils.inputStreamToString(files1[0]);
        source = source.replace("\r\n","");
        source = source.replace("\u0000","");
        System.out.println(source);
        // 获取编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 标准文件管理器

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        MyJavaFileManage myJavaFileManage = new MyJavaFileManage(fileManager);

        Collection<JavaSourceFromString> collection = new ArrayList<>();
        collection.add(new JavaSourceFromString("D:/project/学习/TestHotUpdate/src/main/java/com/cx/Test", source));

        // 将文件转为 JavaFileObject
        Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));

        // 文件管理器 + 文件列表
        compiler.getTask(null, myJavaFileManage, diagnostics, null, null, collection).call();

        byte[] result = myJavaFileManage.getOutClassSource().get("");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:/Users/59780/Desktop/Test.class"));
        fileOutputStream.write(result);

        Iterable<? extends JavaFileObject> compilationUnits2 =
                fileManager.getJavaFileObjects(files2); // use alternative method
// reuse the same file manager to allow caching of jar files
        compiler.getTask(null, myJavaFileManage, null, null, null, compilationUnits2).call();

        fileManager.close();
    }
}
