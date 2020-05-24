package com.cx.web;

import com.cx.work.AuthWork;
import org.yx.annotation.Bean;
import org.yx.annotation.Inject;
import org.yx.http.user.AbstractLoginServlet;
import org.yx.http.user.LoginObject;
import org.yx.http.user.SessionObject;

import javax.servlet.http.HttpServletRequest;

@Bean
public class LoginController extends AbstractLoginServlet {

    @Inject
    private AuthWork authWork;

    @Override
    protected LoginObject login(String token, String user, HttpServletRequest req) {
        String key = req.getParameter("key");
        boolean authLogin = authWork.authLogin(key);
        if (authLogin) {
            SessionObject so = new SessionObject();
            so.setLoginTime(System.currentTimeMillis());
            so.setUserId("admin");
            return LoginObject.success(null, so);
        }
        return LoginObject.error("登录失败");
    }
}
