package com.cx.work;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class OverallContent {

    // 全局缓存 已经被注入的 项目。 key为 用户id 。 val 为jvm的id
    private final static Map<String, String> JOIN_PROJECT_ING = new ConcurrentHashMap<>();

    public static Map<String, String> getJoinProjectIng() {
        return JOIN_PROJECT_ING;
    }

    public static boolean addProjectIng(String userId, String jvmId) {
        return Objects.isNull(JOIN_PROJECT_ING.put(userId, jvmId));
    }

    /**
     * 查看是否存在注入的项目
     * @param userId
     * @return
     */
    public static boolean isJoinIng(String userId){
        return Objects.nonNull(JOIN_PROJECT_ING.get(userId));
    }

}
