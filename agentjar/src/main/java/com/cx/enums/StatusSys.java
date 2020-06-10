package com.cx.enums;

public enum StatusSys {
    NO_MAIN_FAIL(-3, "还没执行主要操作就失败"),
    NOT_EXISTS_FAIL(-2, "结果不存在"),
    FAIL(-1, "执行失败结果"),
    SUCCEED(0, "执行成功"),
    OK_RUN(1, "执行了，但方法内部逻辑执行后的结果不会影响到外层调用"),
    ;

    private int code;
    private String label;

    StatusSys(int recode, String label) {
        this.code = recode;
        this.label = label;
    }

    public boolean isFail() {
        return this.code < 0;
    }

    public boolean isSucceed() {
        return this.code < 0;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
