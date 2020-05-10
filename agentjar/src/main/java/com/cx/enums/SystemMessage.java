package com.cx.enums;

public enum SystemMessage {

    UN_OPERATION("暂未开发");

    private String val;

    SystemMessage(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
