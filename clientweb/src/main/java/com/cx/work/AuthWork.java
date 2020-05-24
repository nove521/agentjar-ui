package com.cx.work;

import org.yx.annotation.Bean;
import org.yx.http.user.LocalUserSession;

@Bean
public class AuthWork {

    public static final String AES_KEY= "123456";

    public static final String TOKEN = "123456789";

    public boolean authLogin(String token) {

        if (TOKEN.equals(token)){
            LocalUserSession session = new LocalUserSession();
            return true;
        }
        return false;

    }

}
