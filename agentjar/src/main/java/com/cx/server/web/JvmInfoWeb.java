package com.cx.server.web;

import com.cx.mode.JvmSimpleInfo;
import com.cx.server.ann.Http;
import com.cx.server.ann.Join;
import com.cx.server.ann.Obj;
import com.cx.server.service.JvmService;

/**
 * jvm相关信息
 */
@Obj
public class JvmInfoWeb {

    @Join
    private JvmService jvmService;

    @Http("/jvminfo/getInfo")
    public JvmSimpleInfo getInfo() {
        return jvmService.getInfo();
    }
}
