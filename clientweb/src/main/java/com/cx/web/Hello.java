package com.cx.web;

import org.yx.annotation.Bean;
import org.yx.annotation.http.Web;
import org.yx.rpc.client.Rpc;

@Bean
public class Hello {

    @Web
    public String hello(){
        System.out.println("hello");
        String ddd = Rpc.call("a.testsoa","dddddd");
        return ddd;
    }

    @Web
    public String hello2(){
        return "heelo2";
    }

    @Web
    public String stop(){
        Rpc.callAsync("a.stop.sumk");
        return "ok";
    }
}
