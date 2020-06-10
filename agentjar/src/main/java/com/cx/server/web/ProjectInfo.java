package com.cx.server.web;

import com.cx.mode.MethodInfo;
import com.cx.server.ann.Http;
import com.cx.server.ann.Join;
import com.cx.server.ann.Obj;
import com.cx.server.ann.Param;
import com.cx.server.service.ProjectInfoService;
import com.cx.server.service.ResourceService;

import java.util.List;
import java.util.Map;

/**
 * 项目相关信息
 */
@Obj
public class ProjectInfo {

    @Join
    private ProjectInfoService projectInfoService;

    @Join
    private ResourceService resourceService;

    /**
     * 获取所有的类
     *
     * @return 类名集合
     */
    @Http("/project/info/getallclass")
    public Map<String, Object> getAllClass(@Param("page") int page, @Param("size") int size, @Param("pattenName") String pattenName) {
        return projectInfoService.getAllClass(page, size, pattenName);
    }

    /**
     * 获取类的信息
     *
     * @return 类名集合
     */
    @Http("/project/info/getclassinfo")
    public Map<String, Object> getClassInfo(@Param("className") String className) throws IllegalAccessException {
        return projectInfoService.getClassInfo(className).toMap();
    }

    @Http("/project/info/getclassallmethods")
    public List<MethodInfo> getClassAllMethods(@Param("className") String className) {
        return projectInfoService.getClassAllMethods(className);
    }

    @Http("/project/info/getClassCodeSource")
    public Map<String, String> getClassCodeSource(@Param("className") String className) {
        return projectInfoService.getClassCodeSource(className);
    }

    @Http("/project/resource/dir")
    public List<Map<String, Object>> getResourceDir() {
        return resourceService.getResourceDir();
    }

    @Http("/project/stop")
    public void stop(@Param("restore") String restore) {
        projectInfoService.stop(restore);
    }

    @Http("/project/resource/file/code")
    public String getresourceFileCode(@Param("fileName") String fileName) {
        return resourceService.getResourceFileCode(fileName);
    }

    @Http("/project/resource/file/saveCode")
    public int saveResourceFileCode(@Param("fileName") String fileName, @Param("code") String code) {
        System.out.println("filename=" + fileName);
        System.out.println("code=" + code);
        return resourceService.saveResourceFileCode(code, fileName).getCode();
    }
}
