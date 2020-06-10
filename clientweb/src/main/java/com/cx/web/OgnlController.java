package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.HttpU;
import com.cx.utils.MyMap;
import com.cx.utils.ReultUtils;
import org.yx.annotation.Bean;
import org.yx.annotation.http.Web;

import java.util.Optional;

@Bean
public class OgnlController {

    @Web(value = "/ognl-test", requireLogin = true)
    public ResultObject<Object> ognlTest(String ognlText) {
        Optional<Object> send = HttpU.send("http://127.0.0.1:19998/hotupdate/ognlTest",
                Object.class, new MyMap<String, String>()
                        .puti("ognlText", ognlText));

        return send.map(ReultUtils::SUCCEED).
                orElseGet(ReultUtils::ERROR_JVM_COMPLIER_FAIL);
    }
}
