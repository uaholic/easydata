package com.guanyanqi.easydata.reader.handler;




/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class LongTypeHandler implements TypeHandler<Long> {

    @Override
    public Long handle(String sourceString) {
        return Long.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Long";
    }
}
