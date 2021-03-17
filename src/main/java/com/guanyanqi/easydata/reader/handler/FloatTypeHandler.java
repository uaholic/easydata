package com.guanyanqi.easydata.reader.handler;




/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class FloatTypeHandler implements TypeHandler<Float> {

    @Override
    public Float handle(String sourceString) {
        return Float.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Float";
    }
}
