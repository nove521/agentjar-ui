package com.cx.utils;

import org.yx.http.HttpContextHolder;

public class TokenUtils {
    public static String getUserId(){
        return HttpContextHolder.getToken();
    }
}
