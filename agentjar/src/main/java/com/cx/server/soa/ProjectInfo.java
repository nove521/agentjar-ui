package com.cx.server.soa;

import com.cx.server.service.ProjectInfoService;
import org.yx.annotation.Bean;
import org.yx.annotation.Inject;
import org.yx.annotation.http.Web;

import java.util.List;
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
     *
     * @return 类名集合
     */
    @Web("/project/info/getallclass")
    public Map<String, Object> getAllClass(int page, int size, String pattenName) {
        return projectInfoService.getAllClass(page, size, pattenName);
    }

    /**
     * 获取类的信息
     *
     * @return 类名集合
     */
    @Web("/project/info/getclassinfo")
    public Map<String, Object> getClassInfo(String className) {
        return projectInfoService.getClassInfo(className).toMap();
    }

    @Web("/project/info/getClassCodeSource")
    public Map<String, String> getClassCodeSource(String className) {
        return projectInfoService.getClassCodeSource(className);
    }

    @Web("/project/stop")
    public void stop() {
        projectInfoService.stop();
    }
}
