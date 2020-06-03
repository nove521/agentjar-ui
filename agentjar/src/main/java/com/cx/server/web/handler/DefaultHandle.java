package com.cx.server.web.handler;

import com.cx.server.web.handler.model.RequestDefine;
import com.cx.utils.IoUtils;
import com.cx.utils.StrUtils;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DefaultHandle extends MyAbstractHandler {

    public DefaultHandle(RequestDefine requestDefine) {
        super(requestDefine);
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        List<Object> paramsObject = this.getParamsObject(httpServletRequest);
        PrintWriter out = httpServletResponse.getWriter();
        try {
            String result = this.invoke(paramsObject);
            out.print(result);
        } catch (InvocationTargetException | IllegalAccessException e) {
            out.print(IoUtils.throwableErrToStr(e));
        }
        request.setHandled(true);
    }

    public String invoke(List<Object> paramsObject) throws InvocationTargetException, IllegalAccessException {
        Method method = this.requestDefine.getMethod();
        Object object = this.requestDefine.getObject();
        Object invoke;
        try {
            invoke = method.invoke(object, paramsObject.toArray());
        } catch (Exception e) {
            return IoUtils.throwableErrToStr(e);
        }
        if (this.requestDefine.getReturnType().equals(void.class)) {
            return null;
        }
        return StrUtils.json().toJson(invoke, this.requestDefine.getReturnType());
    }

}
