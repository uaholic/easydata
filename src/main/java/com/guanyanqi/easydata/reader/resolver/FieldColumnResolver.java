package com.guanyanqi.easydata.reader.resolver;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public interface FieldColumnResolver<T> {
    List<Field> generateColumnFields(Class<T> objectType);
}
