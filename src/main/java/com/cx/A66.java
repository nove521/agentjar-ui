package com.cx;

public class A66 {
    public static void main(String[] args) throws Exception {
//        List<VirtualMachineDescriptor> list = VirtualMachine.list();
//        for (VirtualMachineDescriptor vmd : list) {
//            System.out.println(" 虚拟机： " + vmd.displayName());
//            if (vmd.displayName().endsWith("simple-0.0.1.jar")) {
//                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
//
//                String classPath = "D:/project/simple/target/classes/com/example/simple/controller/HelloController.class";
//                virtualMachine.loadAgent("D:/project/学习/TestHotUpdate/target/TestHotUpdate-1.0.jar ", classPath);
//                System.out.println("ok");
//                virtualMachine.detach();
//            }
//        }
        shenc("D:/project/学习/TestHotUpdate/src/main/java/com/cx/Test.java");
//        shenc("D:/project/simple/src/main/java/com/example/simple/controller/HelloController.java");
    }

    private static void shenc(String className){
//        MyFileObject simpleJavaFileObject = new MyFileObject(className);
//        byte[] b = simpleJavaFileObject.getByteCode();
//        System.out.println(b.length);
        DynamicCompiler dynamicCompiler = new DynamicCompiler();
        byte[]b = dynamicCompiler.getClassByte(className);
        System.out.println(b.length);
    }
 
}