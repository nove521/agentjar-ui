package com.cx.server.web;

import com.cx.server.ann.Http;
import com.cx.server.ann.Join;
import com.cx.server.ann.Obj;
import com.cx.server.ann.Param;
import com.cx.server.service.HotUpdateService;

/**
 * 代码热部署
 */
@Obj
public class HotUpdate {

    @Join
    private HotUpdateService hotUpdateService;

    @Http("/hotupdate/updateClassByString")
    public String updateClassByString(@Param("className") String className, @Param("javaCode") String javaCode) {
        return hotUpdateService.updateClassByString(className, javaCode);
    }

    @Http("/hotupdate/ognltest")
    public String ognltest() {
        return hotUpdateService.ognltest();
    }

    @Http("/hotupdate/invoke")
    public Object invoke(@Param("className") String className,
                         @Param("methodName") String methodName,
                         @Param("paramsJson") String paramsJson) {
        return hotUpdateService.invoke(className, methodName,paramsJson);
    }

}
