package com.cx.server.soa;

import com.cx.agent.Session;
import org.yx.annotation.Bean;
import org.yx.annotation.Inject;
import org.yx.annotation.rpc.Soa;
import org.yx.main.SumkServer;

@Bean
public class TestSoa {

    @Soa("a.testsoa")
    public String repeat(String s){
        return s;
    }

    @Soa("a.ishaveIns")
    public String ishaveIns(String s){
        boolean haveIns = Session.isHaveIns();
        return String.valueOf(haveIns);
    }

    @Soa("a.stop.sumk")
    public void stop(){
        SumkServer.stop();
        System.out.println("退出代理");
    }

    @Soa("a.getInfo")
    public String info(){
        return Session.info();
    }
}
