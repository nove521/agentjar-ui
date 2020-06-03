package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.*;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.*;

@Bean
public class ProjectInfoController {

    @Web(value = "/get-all-class", cnName = "获取项目所有加载的类", requireLogin = true)
    @SuppressWarnings("unchecked")
    public ResultObject<Map<String, Object>> getAllClass(@Param(value = "页数", required = false) Integer page,
                                                         @Param(value = "一页大小", required = false) Integer size,
                                                         @Param(value = "类命匹配", required = false) String pattenName) {

        Optional<Map> send = HttpU.send("http://127.0.0.1:19998/project/info/getallclass",
                Map.class,
                new MyMap<String, String>()
                        .puti("page", U.s(page, 1))
                        .puti("pattenName", U.s(pattenName, ""))
                        .puti("size", U.s(size, 10)));
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.EMPTY_MAP();
    }

    @Web(value = "/get-class-info", cnName = "获取类信息", requireLogin = true)
    public ResultObject<Map<String, Object>> getClassInfo(@Param(value = "类名") String className) {
        Optional<Map> send = HttpU.send("http://127.0.0.1:19998/project/info/getclassinfo",
                Map.class,
                new MyMap<String, String>()
                        .puti("className", className));
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_NOINFO();
    }

    @Web(value = "/getClassCodeSource", cnName = "获取类源码", requireLogin = true)
    public ResultObject<Map<String, Object>> getClassCodeSource(String className) {

        Optional<Map> send = HttpU.send("http://127.0.0.1:19998/project/info/getClassCodeSource",
                Map.class,
                new MyMap<String, String>()
                        .puti("className", className));
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_NOINFO();
    }

    @Web(value = "/get-class-all-methods", cnName = "获取类的所有方法", requireLogin = true)
    public ResultObject<List<Map<String, String>>> getClassAllMethods(String className) {
        Optional<List> send = HttpU.send("http://127.0.0.1:19998/project/info/getclassallmethods",
                List.class,
                new MyMap<String, String>()
                        .puti("className", className));
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_NOINFO();
    }

    @Web(value = "/get-resource-dir", cnName = "获取资源目录", requireLogin = true)
    public ResultObject<List<Map<String, Object>>> getResourceDir() {
        Optional<List> send = HttpU.send("http://127.0.0.1:19998/project/resource/dir",
                List.class, Collections.emptyMap());
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_NOINFO();
    }

    @Web(value = "/get-resource-file-code", cnName = "获取文件内容", requireLogin = true)
    public ResultObject<String> getresourceFileCode(String fileName) {
        Optional<String> send = HttpU.send("http://127.0.0.1:19998/project/resource/file/code",
                String.class, new MyMap<String, String>().puti("fileName", fileName));
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_NOINFO();
    }

    @Web(value = "/get-resource-file-save-code", cnName = "获取文件内容", requireLogin = true)
    public ResultObject<Integer> saveResourceFileCode(String fileName, String code) {
        Optional<Integer> send = HttpU.send("http://127.0.0.1:19998/project/resource/file/saveCode",
                Integer.class,
                new MyMap<String, String>().
                        puti("code", code).
                        puti("fileName", fileName));
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_NOINFO();
    }
}
