package com.guanyanqi.easydata.writer.resolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public abstract class GenerateStringResolver<T> extends GenerateMapperResolver<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenerateStringResolver.class);

    public List<String> resolve(List<T> objects, List<Field> columnFields) {
        List<String> result = new ArrayList<>();
        for (T object : objects) {
            LinkedHashMap<Field, String> resolveMapper = resolveMapper(object, columnFields);
            result.add(Joiner.on(separator()).join(resolveMapper.values()));
        }
        return result;
    }

    public String generateTitle(List<Field> columnFields) {
        LinkedHashMap<Field, String> titleMap = generateTitleMap(columnFields);
        return Joiner.on(separator()).join(titleMap.values());
    }

    public abstract String separator();
}
