package com.cx.utils;

import java.util.Objects;

public class U {

    public static <T> T t(T t, T defaults){
        if (Objects.isNull(t)){
            return defaults;
        }
        return t;
    }

}
