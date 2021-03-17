package com.guanyanqi.easydata.reader.handler;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class StringTypeHandler implements TypeHandler<String> {

    @Override
    public String handle(String sourceString) {
        return sourceString;
    }

    @Override
    public String getType() {
        return "java.lang.String";
    }
}
