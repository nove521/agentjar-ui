package com.cx.server.web.handler.model;

import com.cx.server.ann.Param;
import com.cx.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class RequestDefine {

    private List<ParamInfo> paramList;

    private Class<?> returnType;

    private Method method;

    private Object object;

    public RequestDefine(Method method, Object object) {
        this.method = method;
        this.object = object;
        this.paramList = new ArrayList<>();
        filterParams(method.getParameters());
        this.returnType = method.getReturnType();
    }

    private void filterParams(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            Annotation getAnn = ClassUtils.equalsGetAnn(parameter.getAnnotations(), Param.class);
            if (Objects.nonNull(getAnn)) {
                Param param = (Param) getAnn;
                paramList.add(new ParamInfo(param.value(), parameter));
            }
        }
    }

    public List<ParamInfo> getParamList() {
        return paramList;
    }

    public void setParamList(List<ParamInfo> paramList) {
        this.paramList = paramList;
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

    public static class ParamInfo {
        private String lable;
        private Parameter parameter;

        public ParamInfo(String lable, Parameter parameter) {
            this.lable = lable;
            this.parameter = parameter;
        }

        public String getLable() {
            return lable;
        }

        public Parameter getParameter() {
            return parameter;
        }
    }
}
