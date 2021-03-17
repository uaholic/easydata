package com.guanyanqi.easydata.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatePattern {
    String value() default "yyyy-MM-dd HH:mm:ss";
}
