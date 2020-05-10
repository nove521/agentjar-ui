package com.cx.soa;

import org.yx.annotation.Bean;
import org.yx.annotation.rpc.Soa;
import org.yx.main.SumkServer;

@Bean
public class TestSoa {
    @Soa("a.testsoa")
    public String repeat(String s){
        return s;
    }

    @Soa("a.stop.sumk")
    public void stop(){
        SumkServer.stop();
        System.out.println("退出代理");
    }
}
