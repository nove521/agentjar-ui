package com.cx;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class A66 {
    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        AtomicInteger x = new AtomicInteger();
        list.forEach((i)-> {System.out.println( "虚拟机 " + x + " : " + i.displayName()); x.getAndIncrement();});

        // 选择要搞正在运行 的 jar 或类
        int index =(char) System.in.read() - '0';

        VirtualMachine virtualMachine = VirtualMachine.attach(list.get(index));

        // 这个是我准备替换的class文件
        String classPath = "D:/project/simple/target/classes/com/example/simple/controller/HelloController.class";
//        virtualMachine.loadAgent("D:/project/学习/TestHotUpdate/agentjar/target/agentjar-1.0.jar ", classPath);
        virtualMachine.loadAgent("D:/project/学习/TestHotUpdate/ttttttt/target/ttttttt-1.0.jar ", classPath);
        System.out.println("ok");

        System.in.read();
        System.in.read();
        virtualMachine.detach();


//        for (VirtualMachineDescriptor vmd : list) {
//            System.out.println(" 虚拟机： " + vmd.displayName());
//            if (vmd.displayName().endsWith("simple-0.0.1.jar")) {
//
//            }
//        }

//        ClassLoader classLoader = A66.class.getClassLoader();
//        System.out.println(classLoader.getResource("TestHotUpdate.jar"));
    }

    private static void shenc(String className){
//        MyFileObject simpleJavaFileObject = new MyFileObject(className);
//        byte[] b = simpleJavaFileObject.getByteCode();
//        System.out.println(b.length);
//        DynamicCompiler dynamicCompiler = new DynamicCompiler();
//        byte[]b = dynamicCompiler.getClassByte(className);
//        System.out.println(b.length);
    }
 
}