package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.HttpU;
import com.cx.utils.MyMap;
import com.cx.utils.ReultUtils;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.Collections;
import java.util.Optional;

/**
 * 代码热部署
 */
@Bean
public class HotUpdateController {

    @Web(value = "/updateClass", cnName = "热部署类", requireLogin = true)
    public ResultObject<String> updateClassByString(@Param("类名") String className,
                                                    @Param("java 源码") String javaCode) {

        Optional<String> send = HttpU.send("http://127.0.0.1:18088/rest/hotupdate/updateClassByString",
                String.class,
                new MyMap<String, String>()
                        .puti("className", className)
                        .puti("javaCode", javaCode));
        return send.<ResultObject<String>>
                map(ReultUtils::SUCCEED).
                orElseGet(ReultUtils::ERROR_JVM_COMPLIER_FAIL);

    }

    @Web(value = "/ognl-test", requireLogin = true)
    public ResultObject<Object> ognlTest(){
        Optional<Object> send = HttpU.send("http://127.0.0.1:18088/rest/hotupdate/ognltest",
                Object.class, Collections.emptyMap());

        return send.<ResultObject<Object>>
                map(ReultUtils::SUCCEED).
                orElseGet(ReultUtils::ERROR_JVM_COMPLIER_FAIL);
    }

    @Web("/invoke-method")
    public ResultObject<Object> invoke(String className, String methodName){

        Optional<Object> send = HttpU.send("http://127.0.0.1:18088/rest/hotupdate/invoke",
                Object.class, new MyMap<String, String>()
                        .puti("className",className)
                        .puti("methodName",methodName));

        return send.<ResultObject<Object>>
                map(ReultUtils::SUCCEED).
                orElseGet(ReultUtils::ERROR_JVM_COMPLIER_FAIL);
    }
}
