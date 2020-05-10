package com.cx.server.soa;

import com.cx.mode.ClassInfo;
import com.cx.server.service.ProjectInfoService;
import org.yx.annotation.Bean;
import org.yx.annotation.Inject;
import org.yx.annotation.rpc.Soa;

import java.util.Map;

/**
 * 项目相关信息
 */
@Bean
public class ProjectInfo {

    @Inject
    private ProjectInfoService projectInfoService;

    /**
     * 获取所有的类
     * @return 类名集合
     */
    @Soa("project.info.getallclass")
    public Map<String,String> getAllClass(int page,int size){
        return projectInfoService.getAllClass(page,size);
    }

    /**
     * 获取所有的类
     * @return 类名集合
     */
    @Soa("project.info.getclassinfo")
    public Map<String,Object> getClassInfo(String className){
        return projectInfoService.getClassInfo(className).toMap();
    }

}
