package com.cx.web;

import com.cx.utils.RpcU;
import com.cx.utils.U;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.Map;

@Bean
public class ProjectInfoController {

    @Web(value = "/getAllClass", cnName = "获取项目所有加载的类")
    @SuppressWarnings("unchecked")
    public Map<String, String> getAllClass(@Param(value = "页数", required = false) Integer page,
                                           @Param(value = "一页大小", required = false) Integer size) {

        return RpcU.call("project.info.getallclass",
                Map.class,
                U.t(page, 1),
                U.t(size, 10)
        );

    }

    @Web(value = "/getClassInfo", cnName = "获取类信息")
    public Map<String, Object> getClassInfo(@Param(value = "类名") String className) {
        return RpcU.call("project.info.getclassinfo", Map.class, className);
    }
}
