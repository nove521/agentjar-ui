package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.*;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;
import org.yx.rpc.client.Rpc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Bean
public class ProjectInfoController {

    @Web(value = "/get-all-class", cnName = "获取项目所有加载的类")
    @SuppressWarnings("unchecked")
    public ResultObject<Map<String, Object>> getAllClass(@Param(value = "页数", required = false) Integer page,
                                                         @Param(value = "一页大小", required = false) Integer size,
                                                         @Param(value = "类命匹配", required = false) String pattenName) {

        Map<String, Object> list = HttpU.send("http://127.0.0.1:18088/rest/project/info/getallclass",
                Map.class,
                new MyMap<String, String>()
                        .puti("page", U.s(page, 1))
                        .puti("pattenName", U.s(pattenName, ""))
                        .puti("size", U.s(size, 10)));
        return ReultUtils.SUCCEED(list);
    }

    @Web(value = "/get-class-info", cnName = "获取类信息")
    public ResultObject<Map<String, Object>> getClassInfo(@Param(value = "类名") String className) {
        Map<String, Object> info = HttpU.send("http://127.0.0.1:18088/rest/project/info/getclassinfo",
                Map.class,
                new MyMap<String, String>()
                        .puti("className", className));
        return ReultUtils.SUCCEED(info);
    }

    @Web(value = "/getClassCodeSource", cnName = "获取类源码")
    public ResultObject<Map<String, Object>> getClassCodeSource(String className) {

        Map<String, Object> result = HttpU.send("http://127.0.0.1:18088/rest/project/info/getClassCodeSource",
                Map.class,
                new MyMap<String, String>()
                        .puti("className", className));
        return ReultUtils.SUCCEED(result);
    }

}
