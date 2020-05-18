package com.cx.javaCompiler;

import com.cx.server.service.ProjectInfoService;
import com.cx.utils.ClassUtils;
import com.cx.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.Map;

public class MyCompilerTest {

    @Test
    public void start() throws IOException {

        File file = new File("D:/project/学习/TestHotUpdate/agentjar/src/main/java/com/cx/test/SimpleClass.java");
        String srouce = IoUtils.inputStreamToString(file);

        MyCompiler myCompiler = new MyCompiler(null);
        Map<String, byte[]> resultMap = myCompiler.start("com.cx.test.SimpleClass", srouce);
        resultMap.keySet().forEach(key -> {
            byte[] result = resultMap.get(key);
            IoUtils.toFileByByte(result,"C:/Users/59780/Desktop/by/" + key.substring(key.lastIndexOf(".") + 1) +".class");
        });

    }
    
    @Test
    public void str(){
        String x = "package comxxx.work;" +
                "" +
                "import comxxx.aaa.A;" +
                "import comxxx.bbb.B;" +
                "import comxxx.ddd.D;" +
                "" +
                "import java.util.Date;" +
                "" +
                "public class Index {" +
                "" +
                "    public static void main(String[] args) throws InterruptedException {" +
                "        Thread thread = new Thread(new Runnable() {" +
                "            public void run() {" +
                "                A a = new A();" +
                "                B b = new B();" +
                "                D c = new D();" +
                "                while (true){" +
                "                    System.out.println(\"++++++++++++++\");" +
                "                    a.printStringA();" +
                "                    b.printStringB();" +
                "                    c.printStringD();" +
                "                    System.out.println(new Date().getTime());" +
                "                    try {" +
                "                        Thread.sleep(1000);" +
                "                    } catch (InterruptedException e) {" +
                "                        e.printStackTrace();" +
                "                    }" +
                "                }" +
                "            }" +
                "        });" +
                "        thread.run();" +
                "        thread.join();" +
                "    }" +
                "" +
                "}";
        x = x.replace("\n", "");
        System.out.println(x);
    }


    public static void main(String[] args) {
        System.out.println(MyCompilerTest.class.getProtectionDomain().getCodeSource());
    }
}