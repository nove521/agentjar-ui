package com.cx.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldHandle extends AbstractHandler {

    /**
     * <pre>
     * </pre>
     *
     * @author
     * @param target              - 目标请求，可以是一个URI或者是一个转发到这的处理器的名字
     * @param request             - Jetty自己的没有被包装的请求，一个可变的Jetty请求对象
     * @param httpServletRequest  - 被filter或者servlet包装的请求，一个不可变的Jetty请求对象
     * @param httpServletResponse - 响应，可能被filter或者servlet包装过
     * @return
     */
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("1","a");
        jsonObject.addProperty("2","b");
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonObject.toString());
        request.setHandled(true);
    }
}
