package com.guanyanqi.easydata.common.utils;

import java.util.Objects;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class QiUtils {

    public static<T> T replaceNull(T source,T target){
        return Objects.isNull(source)?target:source;
    }
}
