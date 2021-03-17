package com.guanyanqi.easydata.reader.handler;




/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class ShortTypeHandler implements TypeHandler<Short> {

    @Override
    public Short handle(String sourceString) {
        return Short.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Short";
    }
}
