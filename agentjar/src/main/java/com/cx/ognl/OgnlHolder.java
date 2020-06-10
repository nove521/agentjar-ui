package com.cx.ognl;

import com.cx.utils.IoUtils;
import com.cx.utils.StrUtils;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import java.util.Optional;

public class OgnlHolder {

    public static String runOgnl(String expression) {
        OgnlContext context = new OgnlContext();
        Object root = context.getRoot();
        try {
            //@com.example.simple.SimpleApplication@mmm()
            Object obj = Ognl.getValue(expression, context, root);
            return Optional.ofNullable(obj).map(res -> StrUtils.json().toJson(res)).orElse("执行成功");
        } catch (OgnlException e) {
            return IoUtils.throwableErrToStr(e);
        }
    }

}
