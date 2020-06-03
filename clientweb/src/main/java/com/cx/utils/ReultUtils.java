package com.cx.utils;

import com.cx.model.ResultObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ReultUtils {

    SUCCEED("200", "请求成功"),
    ERROR("400", "请求失败"),
    ERROR_JVM_COMPLIER_FAIL("401", "更新失败。可能是JVM已关闭"),
    ERROR_LOGIN("402", "登录失败"),
    ERROR_NOINFO("422", "请求不到消息");

    private String code;
    private String msg;

    public static ResultObject<Map<String, Object>> EMPTY_MAP() {
        Map<String, Object> reuslt = new HashMap<>();
        reuslt.put("total", "0");
        reuslt.put("list", Collections.emptyList());
        return SUCCEED(reuslt);
    }

    public static ResultObject<List<Object>> EMPTY_LIST() {
        return SUCCEED(Collections.emptyList());
    }

    public static <T> ResultObject<T> SUCCEED_MSG(String msg) {
        return new ResultObject<>(SUCCEED.getCode(), msg, null);
    }

    public static <T> ResultObject<T> SUCCEED() {
        return new ResultObject<>(SUCCEED.getCode(), SUCCEED.getMsg(), null);
    }

    public static <T> ResultObject<T> SUCCEED(T data) {
        return new ResultObject<>(SUCCEED.getCode(), SUCCEED.getMsg(), data);
    }

    public static <T> ResultObject<T> ERROR() {
        return new ResultObject<>(ERROR.getCode(), ERROR.getMsg(), null);
    }

    public static <T> ResultObject<T> ERROR(String msg) {
        return new ResultObject<>(ERROR.getCode(), msg, null);
    }

    public static <T> ResultObject<T> ERROR(T data) {
        return new ResultObject<>(SUCCEED.getCode(), SUCCEED.getMsg(), data);
    }

    public static <T> ResultObject<T> ERROR_LOGIN() {
        return new ResultObject<>(ERROR_LOGIN.getCode(), ERROR_LOGIN.getMsg(), null);
    }

    public static <T> ResultObject<T> ERROR_JVM_COMPLIER_FAIL() {
        return new ResultObject<>(ERROR_JVM_COMPLIER_FAIL.getCode(), ERROR_JVM_COMPLIER_FAIL.getMsg());
    }

    public static <T> ResultObject<T> ERROR_NOINFO() {
        return new ResultObject<>(ERROR_NOINFO.getCode(), ERROR_NOINFO.getMsg());
    }

    ReultUtils(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
