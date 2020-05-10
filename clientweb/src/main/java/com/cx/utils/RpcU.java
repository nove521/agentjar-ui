package com.cx.utils;

import org.yx.rpc.client.Rpc;
import org.yx.util.S;

public class RpcU {

    public static <T> T call(String method,Class<T> clazz, Object... args){
        Rpc.init();
        String result = Rpc.call(method,args);
        return S.json.fromJson(result,clazz);
    }

}
