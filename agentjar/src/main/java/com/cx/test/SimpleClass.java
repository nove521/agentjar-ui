package com.cx.test;

import org.yx.log.Log;

public class SimpleClass {
    public static void main(String[] args) throws InterruptedException {
        Sss s = new Sss();
        while (true){
            Thread.sleep(1000);
            s.test();
            Log.get(SimpleClass.class).info("   -- ");
        }
    }
}
