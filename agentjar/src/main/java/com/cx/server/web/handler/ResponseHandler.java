package com.cx.server.web.handler;

import com.cx.server.web.handler.model.RequestDefine;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseHandler extends MyAbstractHandler {

    public ResponseHandler() {
    }

    public ResponseHandler(RequestDefine requestDefine) {
        super(requestDefine);
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
