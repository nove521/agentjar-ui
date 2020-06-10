package com.cx.server.service;

import com.cx.agent.Session;
import com.cx.enums.StatusSys;
import com.cx.server.ann.Obj;
import com.cx.utils.ClassUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * 资源目录
 */
@Obj
public class ResourceService {

//    private static final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ResourceService.class);
//
//    static {
//        logger.setLevel(Level.ERROR);
//    }

    /**
     * 获取文件 内容
     *
     * @param fileName 文件相对目录
     * @return 内容
     */
    public String getResourceFileCode(String fileName) {
        try {
            URL url = getEntranceUrl().orElseThrow(() -> new URISyntaxException(fileName, "无法获取类"));
            String urlFileCode = ClassUtils.getUrlFileCode(url, fileName);
            return Optional.ofNullable(urlFileCode).orElse("找不到这个文件");
        } catch (URISyntaxException | IOException e) {
            return String.format("获取文件内容异常 %s", e.getMessage());
        }
    }

    /**
     * 修改文件内容
     *
     * @param fileName 文件相对目录
     * @param code     内容
     * @return 结果
     */
    public StatusSys saveResourceFileCode(String fileName, String code) {
        try {
            URL url = getEntranceUrl().orElseThrow(() -> new URISyntaxException(fileName, "无法获取类"));
            return ClassUtils.editUrlFileCode(url, fileName, code, true);
        } catch (URISyntaxException e) {
//            logger.error("文件修改失败: {}", e.getMessage());
            return StatusSys.FAIL;
        }
    }

    /**
     * 返回项目目录
     *
     * @return 目录列表，兼容前端格式
     */
    public List<Map<String, Object>> getResourceDir() {
        try {
            URL url = getEntranceUrl().orElseThrow(() -> new URISyntaxException("", "无法获取类"));
            return ClassUtils.getDirMapDataByUrl(url);
        } catch (URISyntaxException e) {
//            logger.error("获取不到目录: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 抽取JVM 一个文件URL
     *
     * @return 当前jvm 下 URL
     */
    private Optional<URL> getEntranceUrl() {
        List<Class<?>> classCache = Session.getClassCache();
        Class<?> aClass = classCache.get(0);
        try {
            Enumeration<URL> resources = aClass.getClassLoader().getResources("");
            if (resources.hasMoreElements()) {
                return Optional.of(resources.nextElement());
            }
        } catch (IOException e) {
//            logger.error("找不到合适类");
        }
        return Optional.empty();
    }
}
