package com.cx.test;

import org.yx.log.Log;

public class SimpleClass {
    public static void main(String[] args) throws InterruptedException {
        Sss s = new Sss();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("222222");
            }
        });
        thread.start();
    }
}