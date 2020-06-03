package com.cx.server.ann;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Http {
    /**
     * url
     * @return
     */
    String value() default "";

    /**
     * 是否需要验证
     * @return
     */
    boolean mustToken() default false;
}
