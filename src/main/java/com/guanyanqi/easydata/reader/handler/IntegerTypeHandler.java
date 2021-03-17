package com.guanyanqi.easydata.reader.handler;




/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {

    @Override
    public Integer handle(String sourceString) {
        return Integer.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Integer";
    }
}
