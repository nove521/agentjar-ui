package com.cx.server.service;

import com.cx.ognl.OgnlHolder;
import com.cx.server.ann.Obj;

@Obj
public class OgnlService {

    public String ognlTest(String ognlText) {
        return OgnlHolder.runOgnl(ognlText);
    }
}
