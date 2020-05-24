package com.cx.web;

import com.cx.model.InnerObject;
import com.cx.model.JvmException;
import com.cx.model.Options;
import com.cx.model.ResultObject;
import com.cx.utils.*;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;

import java.util.List;
import java.util.Map;

@Bean
public class SystemController {

    @Web(value = "/get-projects", cnName = "获取正在运行中的虚拟机", requireLogin = true)
    public ResultObject<List<Map<String, String>>> getProjects() {
        return ReultUtils.SUCCEED(VMutils.getAllJVMs());
    }

    @Web(value = "/join-project", cnName = "jar包注入项目", requireLogin = true)
    public ResultObject<Map<String, String>> joinProject(@Param("jvm的id") String id) {
        try {
            InnerObject innerObject = new InnerObject(TokenUtils.getUserId());
            Options options = new Options(innerObject.getPort());
            VMutils.joinProject(id);

        } catch (JvmException e) {
            return ReultUtils.ERROR(e.getMessage());
        }
        return ReultUtils.SUCCEED("启动成功");
    }


    @Web(value = "/stop-project", cnName = "退出项目", requireLogin = true)
    public ResultObject<Map<String, String>> stop(@Param("是否还原更改") String restore) {
        HttpU.sendAsync("http://127.0.0.1:18088/rest/project/stop",
                new MyMap<String, String>()
                        .puti("restore", restore));
        return ReultUtils.SUCCEED("退出成功");
    }

}
