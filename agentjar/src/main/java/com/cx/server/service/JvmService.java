package com.cx.server.service;

import com.cx.mode.JvmSimpleInfo;
import com.cx.server.ann.Obj;

@Obj
public class JvmService {

    public JvmSimpleInfo getInfo() {
        JvmSimpleInfo jvmSimpleInfo = new JvmSimpleInfo();
        jvmSimpleInfo.fill();
        return jvmSimpleInfo;
    }

}
