package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.HttpU;
import com.cx.utils.ReultUtils;
import com.cx.utils.VMutils;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.yx.annotation.Bean;
import org.yx.annotation.Param;
import org.yx.annotation.http.Web;
import org.yx.rpc.client.Rpc;
import org.yx.util.StringUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Bean
public class SystemController {

    @Web(value = "/get-projects", cnName = "获取正在运行中的虚拟机")
    public ResultObject<List<Map<String, String>>> getProjects() {
        return ReultUtils.SUCCEED(VMutils.getAllJVMs());
    }

    @Web(value = "/join-project", cnName = "jar包注入项目")
    public ResultObject<Map<String, String>> joinProject(@Param("jvm的id") String id) {
        VirtualMachineDescriptor jvM = VMutils.getJVM(id);
        if (Objects.isNull(jvM)) {
            return ReultUtils.ERROR("未找到相应的jvm，可能目标已关闭，请重新请求查询");
        }

        try {
            VMutils.launch(jvM, VMutils.JAR_PATH);
        } catch (IOException | AttachNotSupportedException | AgentLoadException | AgentInitializationException e) {
            e.printStackTrace();
            return ReultUtils.ERROR("启动注入失败");
        }

        return ReultUtils.SUCCEED("启动成功");
    }


    @Web(value = "/stop-project", cnName = "退出项目")
    public ResultObject<Map<String, String>> stop(){
        HttpU.send("http://127.0.0.1:18088/rest/project/stop",null, Collections.emptyMap());
        return ReultUtils.SUCCEED("退出成功");
    }

}
