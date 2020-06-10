package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.HttpU;
import com.cx.utils.MyMap;
import com.cx.utils.ReultUtils;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.Optional;

/**
 * 代码热部署
 */
@Bean
public class HotUpdateController {

    @Web(value = "/updateClass", cnName = "热部署类", requireLogin = true)
    public ResultObject<String> updateClassByString(@Param("类名") String className,
                                                    @Param("java 源码") String javaCode) {

        Optional<String> send = HttpU.send("http://127.0.0.1:19998/hotupdate/updateClassByString",
                String.class,
                new MyMap<String, String>()
                        .puti("className", className)
                        .puti("javaCode", javaCode));
        return send.map(ReultUtils::SUCCEED).
                orElseGet(ReultUtils::ERROR_JVM_COMPLIER_FAIL);

    }

    @Web("/invoke-method")
    public ResultObject<String> invoke(String className, String methodName, String paramsJson) {

        Optional<String> send = HttpU.send("http://127.0.0.1:19998/hotupdate/invoke",
                String.class, new MyMap<String, String>()
                        .puti("className", className)
                        .puti("methodName", methodName)
                        .puti("paramsJson", paramsJson));

        return send.map(ReultUtils::SUCCEED).
                orElseGet(ReultUtils::SUCCEED);
    }
}
