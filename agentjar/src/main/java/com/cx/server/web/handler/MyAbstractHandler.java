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
        List<RequestDefine.ParamInfo> paramList = this.requestDefine.getParamList();
        for (RequestDefine.ParamInfo paramInfo : paramList) {
            Parameter parameter = paramInfo.getParameter();
            String paramsStr = httpServletRequest.getParameter(paramInfo.getLable());
            Object obj = StrUtils.changeTo(paramsStr, parameter.getType());
            paramsObj.add(obj);
        }
        return paramsObj;
    }

}
