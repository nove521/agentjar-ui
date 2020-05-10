package com.cx.agent;

import org.yx.main.SumkServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Collections;

public class LoadedAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent Main called");
        System.out.println("agentArgs : " + agentArgs);
        Class<?>[] allClass = inst.getAllLoadedClasses();
        for (Class<?> c :allClass){
//            System.out.println( "已加载的类：" + c.getSimpleName());
            if (c.getSimpleName().endsWith("HelloController")){

                ClassLoader classLoader = c.getClassLoader();
                byte []b = new byte[0];
                try {
                    b = readFileToByte(agentArgs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(" 加载的类的大小：" + b.length);
                if (b.length > 0){
                    ClassDefinition classDefinition = new ClassDefinition(c,b);
                    try {
                        inst.redefineClasses(classDefinition);
                    } catch (ClassNotFoundException | UnmodifiableClassException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            SumkServer.start(Collections.singletonList("nohttp"));
        }catch (Error e){
            e.printStackTrace();
        }

        System.out.println("退出 agentjar");
    }

    private static byte[] readFileToByte(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        byte[] b = new byte[fileInputStream.available()];
        fileInputStream.read(b);
        fileInputStream.close();
        return b;
    }

    public static void main(String[] args) {
        SumkServer.start();
    }
}
