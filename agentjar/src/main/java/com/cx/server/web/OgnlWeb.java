package com.cx.server.web;

import com.cx.server.ann.Http;
import com.cx.server.ann.Join;
import com.cx.server.ann.Obj;
import com.cx.server.ann.Param;
import com.cx.server.service.OgnlService;

@Obj
public class OgnlWeb {

    @Join
    private OgnlService ognlService;

    @Http("/hotupdate/ognlTest")
    public String ognlTest(@Param("ognlText") String ognlText) {
        return ognlService.ognlTest(ognlText);
    }
}
