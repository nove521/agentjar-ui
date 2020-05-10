package com.cx.javaCompiler;

import com.cx.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MyCompilerTest {

    @Test
    public void start() throws IOException {

        File file = new File("D:/project/学习/TestHotUpdate/agentjar/src/main/java/com/cx/test/SimpleClass.java");
        String srouce = IoUtils.inputStreamToString(file);

        MyCompiler myCompiler = new MyCompiler(null);
        byte [] result = myCompiler.start("com.cx.test.SimpleClass", srouce);
        System.out.println(result.length);
    }
}