package com.cx;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class LoadedAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException, UnmodifiableClassException, ClassNotFoundException {
        System.out.println("Agent Main called");
        System.out.println("agentArgs : " + agentArgs);
        Class<?>[] allClass = inst.getAllLoadedClasses();
        for (Class<?> c :allClass){
//            System.out.println( "已加载的类：" + c.getSimpleName());
            if (c.getSimpleName().endsWith("HelloController")){

                ClassLoader classLoader = c.getClassLoader();
                byte []b = readFileToByte(agentArgs);
                System.out.println(" 加载的类的大小：" + b.length);
                if (b.length > 0){
                    ClassDefinition classDefinition = new ClassDefinition(c,b);
                    inst.redefineClasses(classDefinition);
                }
            }
        }
    }

    private static byte[] readFileToByte(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        byte[] b = new byte[fileInputStream.available()];
        fileInputStream.read(b);
        fileInputStream.close();
        return b;
    }
}
