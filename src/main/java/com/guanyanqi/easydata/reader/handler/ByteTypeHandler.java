package com.guanyanqi.easydata.reader.handler;


/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class ByteTypeHandler implements TypeHandler<Byte> {

    @Override
    public Byte handle(String sourceString) {
        return Byte.valueOf(sourceString);
    }

    @Override
    public String getType() {
        return "java.lang.Byte";
    }
}
