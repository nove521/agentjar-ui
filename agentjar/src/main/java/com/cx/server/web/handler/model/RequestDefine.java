package com.cx.server.web.handler.model;

import com.cx.server.ann.Param;
import com.cx.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class RequestDefine {

    private Map<String, Parameter> paramMap;

    private Class<?> returnType;

    private Method method;

    private Object object;

    public RequestDefine(Method method, Object object) {
        this.method = method;
        this.object = object;
        this.paramMap = new HashMap<>();
        filterParams(method.getParameters());
        this.returnType = method.getReturnType();
    }

    private void filterParams(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            Annotation getAnn = ClassUtils.equalsGetAnn(parameter.getAnnotations(), Param.class);
            if (Objects.nonNull(getAnn)) {
                Param param = (Param) getAnn;
                paramMap.put(param.value(), parameter);
            }
        }
    }

    public Map<String, Parameter> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Parameter> paramMap) {
        this.paramMap = paramMap;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
}
