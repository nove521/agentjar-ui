package com.cx.enums;

public enum SystemMessage {

    UN_OPERATION("暂未开发"),
    UN_INIT_INS("未初始化：Instrumentation");

    private String val;

    SystemMessage(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
