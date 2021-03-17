package com.guanyanqi.easydata.reader.handler;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class BooleanTypeHandler implements TypeHandler<Boolean> {

    @Override
    public Boolean handle(String sourceString) {
        return Boolean.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Boolean";
    }
}
