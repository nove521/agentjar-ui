package com.cx.web;

import com.cx.model.ResultObject;
import com.cx.utils.HttpU;
import com.cx.utils.ReultUtils;
import org.yx.annotation.Bean;
import org.yx.annotation.http.Web;
import org.yx.util.SumkDate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * jvm相关信息
 */
@Bean
public class JvmInfoController {

    @Web("/jvminfo/get-info")
    public ResultObject<Map<String, Object>> getInfo() {
        Optional<Map> send = HttpU.send("http://127.0.0.1:19998/jvminfo/getInfo",
                Map.class, Collections.emptyMap());
        if (send.isPresent()) {
            return ReultUtils.SUCCEED(send.get());
        }
        return ReultUtils.ERROR_JVM_COMPLIER_FAIL();
    }
}
