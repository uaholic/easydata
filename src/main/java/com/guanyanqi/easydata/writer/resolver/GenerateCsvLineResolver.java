package com.guanyanqi.easydata.writer.resolver;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class GenerateCsvLineResolver<T> extends GenerateStringResolver<T> {
    @Override
    public String separator() {
        return ",";
    }
}
