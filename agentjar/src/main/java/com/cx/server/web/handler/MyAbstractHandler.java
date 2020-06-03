package com.cx.server.web.handler;

import com.cx.server.web.handler.model.RequestDefine;
import com.cx.utils.StrUtils;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.*;

public abstract class MyAbstractHandler extends AbstractHandler {

    protected RequestDefine requestDefine;


    public MyAbstractHandler() {
    }

    public MyAbstractHandler(RequestDefine requestDefine) {
        this.requestDefine = requestDefine;
    }

    public List<Object> getParamsObject(HttpServletRequest httpServletRequest) {
        List<Object> paramsObj = new ArrayList<>();
        Map<String, Parameter> paramMap = this.requestDefine.getParamMap();
        Set<String> paramNames = paramMap.keySet();
        for (String name : paramNames) {
            Parameter parameter = paramMap.get(name);
            String paramsStr = httpServletRequest.getParameter(name);
            Object obj = StrUtils.changeTo(paramsStr, parameter.getType());
            paramsObj.add(obj);
        }
        Collections.reverse(paramsObj);
        return paramsObj;
    }

}
