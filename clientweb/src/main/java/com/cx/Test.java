package com.cx;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Sss s = new Sss();
        while (true){
            Thread.sleep(1000);
            s.test();
        }
    }
}
