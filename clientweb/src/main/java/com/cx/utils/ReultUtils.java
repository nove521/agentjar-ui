package com.cx.utils;

import com.cx.model.ResultObject;

public enum ReultUtils {

    SUCCEED("200", "请求成功"),
    ERROR("400", "请求失败"),
    ERROR_JVM_COMPLIER_FAIL("401", "更新失败。可能是JVM已关闭");

    private String code;
    private String msg;

    public static <T> ResultObject<T> SUCCEED(String msg) {
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

    public static <T> ResultObject<T> ERROR_JVM_COMPLIER_FAIL() {
        return new ResultObject<>(ERROR_JVM_COMPLIER_FAIL.getCode(), ERROR_JVM_COMPLIER_FAIL.getMsg());
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
