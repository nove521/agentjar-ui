package com.cx.utils;

import java.util.Objects;

public class U {

    public static <T> T t(T t, T defaults){
        if (Objects.isNull(t)){
            return defaults;
        }
        return t;
    }

    public static String s(Object t, Object defaults){
        if (Objects.isNull(t)){
            return String.valueOf(defaults);
        }
        return String.valueOf(t);
    }

}
