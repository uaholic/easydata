package com.guanyanqi.easydata.reader.handler;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public interface TypeHandler<T> {

    T handle(String sourceString);

    String getType();
}
