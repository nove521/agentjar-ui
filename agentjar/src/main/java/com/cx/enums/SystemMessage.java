package com.cx.enums;

public enum SystemMessage {

    UN_OPERATION("暂未开发"),
    NO_FOUND_BEAN("找不到bean"),
    UN_INIT_INS("未初始化：Instrumentation"),

    ;

    private final String val;

    SystemMessage(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
