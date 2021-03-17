package com.guanyanqi.easydata.reader.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ReflectionUtils;

import com.guanyanqi.easydata.common.annotation.DatePattern;
import com.guanyanqi.easydata.reader.annotation.ReadingIndex;
import com.guanyanqi.easydata.reader.handler.BooleanTypeHandler;
import com.guanyanqi.easydata.reader.handler.ByteTypeHandler;
import com.guanyanqi.easydata.reader.handler.CharacterTypeHandler;
import com.guanyanqi.easydata.reader.handler.DateTypeHandler;
import com.guanyanqi.easydata.reader.handler.DoubleTypeHandler;
import com.guanyanqi.easydata.reader.handler.FloatTypeHandler;
import com.guanyanqi.easydata.reader.handler.IntegerTypeHandler;
import com.guanyanqi.easydata.reader.handler.LongTypeHandler;
import com.guanyanqi.easydata.reader.handler.ShortTypeHandler;
import com.guanyanqi.easydata.reader.handler.StringTypeHandler;
import com.guanyanqi.easydata.reader.handler.TypeHandler;
import com.guanyanqi.easydata.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class FieldIndexMappingResolver {

    private static final Logger logger = LoggerFactory.getLogger(FieldIndexMappingResolver.class);

    private final List<TypeHandler<?>> defaultTypeHandlers = ImmutableList.of(
            new StringTypeHandler(), new BooleanTypeHandler(), new ByteTypeHandler(),
            new CharacterTypeHandler(), new DoubleTypeHandler(), new FloatTypeHandler(),
            new IntegerTypeHandler(), new LongTypeHandler(), new ShortTypeHandler(), new DateTypeHandler());

    private List<TypeHandler<?>> customTypeHandlers;

    private final Map<String, TypeHandler<?>> typeHandlerMap = Maps.newConcurrentMap();

    public FieldIndexMappingResolver() {
        for (TypeHandler<?> defaultTypeHandler : defaultTypeHandlers) {
            logger.debug("Adding default type handler class:{} to handle '{}' type.",
                    defaultTypeHandler.getClass().getName(),defaultTypeHandler.getType());
            typeHandlerMap.put(defaultTypeHandler.getType(), defaultTypeHandler);
        }
    }

    public <T> List<T> resolve(List<Map<Integer, String>> indexMaps, Class<T> targetClass) {
        List<T> result = new ArrayList<>();
        Map<Integer, Field> indexFieldMap = new HashMap<>();
        ReflectionUtils.doWithFields(targetClass,field -> {
            ReadingIndex readingIndexAnnotation = field.getDeclaredAnnotation(ReadingIndex.class);
            if (Objects.nonNull(readingIndexAnnotation)) {
                indexFieldMap.put(readingIndexAnnotation.value(), field);
            }
        });
        for (Map<Integer, String> indexMap : indexMaps) {
            try {
                T instance = targetClass.newInstance();
                indexMap.forEach((index, value) -> {
                    Field field = indexFieldMap.get(index);
                    if (Objects.nonNull(field)) {
                        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(instance);
                        propertyAccessor.setPropertyValue(field.getName(), convertValueType(field, value));
                    }
                });
                result.add(instance);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("create instance {} error ",targetClass.getName(), e);
                throw new RuntimeException("create instance error " + targetClass.getName(), e);
            }
        }
        return result;
    }

    private Object convertValueType(Field field, String value) {
        Class<?> typeClazz = field.getType();
        if (typeClazz.equals(Date.class)) {
            DatePattern pattern = field.getDeclaredAnnotation(DatePattern.class);
            if (Objects.nonNull(pattern)) {
                return DateUtil.dateParse(value, pattern.value());
            }
        }

        String canonicalName = typeClazz.getCanonicalName();
        TypeHandler<?> typeHandler = typeHandlerMap.get(canonicalName);
        if (Objects.nonNull(typeHandler)) {
            return typeHandler.handle(value);
        }

        try {
            return typeClazz.getConstructor(String.class).newInstance(value);
        } catch (NoSuchMethodException e) {
            logger.error("{} cannot find a suitable typeHandler, and the constructor parameter isn't a String type",
                    typeClazz.getCanonicalName(),e);
            throw new RuntimeException(typeClazz.getCanonicalName() + " 未找到对应typeHandler，也没有String类型构造方法", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            logger.error("{} built instance error.",typeClazz.getCanonicalName(),e);
            throw new RuntimeException(typeClazz.getCanonicalName() + " 实例构造异常", e);
        }
    }

    @PostConstruct
    public void addCustomTypeHandlers() {
        for (TypeHandler<?> typeHandler : customTypeHandlers) {
            logger.debug("Adding customer type handler class:{} to handle '{}' type.",
                    typeHandler.getClass().getName(),typeHandler.getType());
            TypeHandler<?> replacedHandler = typeHandlerMap.put(typeHandler.getType(), typeHandler);
            if (Objects.nonNull(replacedHandler)){
                logger.info("The '{}' type handler has been changed by class:{}.",typeHandler.getType(),
                        typeHandler.getClass().getName());
            }
        }
    }

    public List<TypeHandler<?>> getCustomTypeHandlers() {
        return customTypeHandlers;
    }

    public void setCustomTypeHandlers(
            List<TypeHandler<?>> customTypeHandlers) {
        this.customTypeHandlers = customTypeHandlers;
    }
}
