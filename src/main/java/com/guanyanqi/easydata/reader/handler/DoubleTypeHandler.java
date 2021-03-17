package com.guanyanqi.easydata.reader.handler;




/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class DoubleTypeHandler implements TypeHandler<Double> {

    @Override
    public Double handle(String sourceString) {
        return Double.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Double";
    }
}
