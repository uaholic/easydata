package com.guanyanqi.easydata.writer.resolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.guanyanqi.easydata.reader.resolver.FieldColumnResolver;
import org.apache.commons.lang3.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ReflectionUtils;

import com.guanyanqi.easydata.common.annotation.DatePattern;
import com.guanyanqi.easydata.writer.annotation.ExportNull;
import com.guanyanqi.easydata.writer.annotation.ExportOrder;
import com.guanyanqi.easydata.writer.annotation.WritingColumn;
import com.guanyanqi.easydata.writer.bean.FieldInfo;
import com.guanyanqi.easydata.common.enums.DatePatternEnum;
import com.guanyanqi.easydata.common.utils.DateUtil;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class GenerateMapperResolver<T> implements FieldColumnResolver<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenerateMapperResolver.class);

    @Override
    public List<Field> generateColumnFields(Class<T> objectType) {
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        ReflectionUtils.doWithFields(objectType,field->{
            WritingColumn writingColumn = field.getDeclaredAnnotation(WritingColumn.class);
            if (Objects.nonNull(writingColumn)) {
                logger.debug("Adding enable field:{}", field.getName());
                ExportOrder exportOrder = field.getDeclaredAnnotation(ExportOrder.class);
                int order = Objects.isNull(exportOrder) ? 0 : exportOrder.value();
                fieldInfoList.add(new FieldInfo(field, order));
            }
        });
        fieldInfoList.sort(Comparator.comparingInt(FieldInfo::getExportOrder));
        return fieldInfoList.stream().map(FieldInfo::getField).collect(Collectors.toList());
    }

    public LinkedHashMap<Field, String> resolveMapper(T object, List<Field> columnFields) {
        LinkedHashMap<Field, String> resultMap = new LinkedHashMap<>();
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(object);
        for (Field field : columnFields) {
            String value = StringUtils.EMPTY;
            Object propertyValue = propertyAccessor.getPropertyValue(field.getName());
            if (Objects.nonNull(propertyValue)) {
                if (field.getType().equals(Date.class)) {
                    DatePattern pattern = field.getDeclaredAnnotation(DatePattern.class);
                    if (Objects.nonNull(pattern)) {
                        value = DateUtil.dateFormat((Date) propertyValue, pattern.value());
                    } else {
                        value = DateUtil.dateFormat((Date) propertyValue, DatePatternEnum.DEFAULT.getPatten());
                    }
                } else {
                    value = propertyValue.toString();
                }
            } else {
                ExportNull exportNull = field.getDeclaredAnnotation(ExportNull.class);
                if (Objects.nonNull(exportNull)) {
                    value = exportNull.value();
                }
            }
            resultMap.put(field, value);
        }
        return resultMap;
    }

    public LinkedHashMap<Field, String> generateTitleMap(List<Field> columnFields) {
        LinkedHashMap<Field, String> resultMap = new LinkedHashMap<>();
        for (Field field : columnFields) {
            WritingColumn writingColumn = field.getDeclaredAnnotation(WritingColumn.class);
            if (Objects.nonNull(writingColumn)) {
                resultMap.put(field, writingColumn.value());
            }
        }
        return resultMap;
    }
}
