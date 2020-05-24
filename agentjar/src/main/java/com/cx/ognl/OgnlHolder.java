package com.cx.ognl;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class OgnlHolder {

    public static String sayHello() throws OgnlException {
        OgnlContext context = new OgnlContext();
        Object root = context.getRoot();
        Object obj = Ognl.getValue("@com.example.simple.SimpleApplication@mmm()",context,root);
        return "";
    }

}
