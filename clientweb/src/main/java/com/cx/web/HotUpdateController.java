package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.HttpU;
import com.cx.utils.MyMap;
import com.cx.utils.ReultUtils;
import com.cx.utils.RpcU;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.Map;
import java.util.Objects;

/**
 * 代码热部署
 */
@Bean
public class HotUpdateController {

    @Web(value = "/updateClass", cnName = "热部署类")
    public ResultObject<String> updateClassByString(@Param("类名") String className,
                                                    @Param("java 源码") String javaCode) {

        String send = HttpU.send("http://127.0.0.1:18088/rest/hotupdate/updateClassByString",
                String.class,
                new MyMap<String, String>()
                        .puti("className", className)
                        .puti("javaCode", javaCode));
        if (Objects.isNull(send)){
            return ReultUtils.ERROR_JVM_COMPLIER_FAIL();
        }
        return ReultUtils.SUCCEED(send);

    }

}
