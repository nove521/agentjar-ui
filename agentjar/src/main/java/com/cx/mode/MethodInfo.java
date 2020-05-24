package com.cx.mode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodInfo {
    private String name;
    private String returnType;
    private List<String> paramsType;

    public MethodInfo(Method method) {
        this.name = method.getName();
        this.returnType = method.getReturnType().getName();
        this.paramsType = Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList());
    }

    public MethodInfo(String name, String returnType, List<String> paramsType) {
        this.name = name;
        this.returnType = returnType;
        this.paramsType = paramsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<String> getParamsType() {
        return paramsType;
    }

    public void setParamsType(List<String> paramsType) {
        this.paramsType = paramsType;
    }
}
