package com.cx.web;

import com.cx.utils.RpcU;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.Map;

/**
 * 代码热部署
 */
@Bean
public class HotUpdateController {

    @Web(value = "/updateClass", cnName = "热部署类")
    public String updateClassByString(@Param("类名") String className,
                                      @Param("java 源码") String javaCode) {

        return RpcU.call("hotupdate.updateClassByString", String.class, className, javaCode);

    }

}
