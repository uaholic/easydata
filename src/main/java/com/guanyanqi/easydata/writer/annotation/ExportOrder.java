package com.guanyanqi.easydata.writer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于明确GenerateStringResolver中将对象转换为String时排列的顺序。
 * 注意：value的值并不意味着一定排列到指定的位置，他仅代表一个排序的权重，value小的排在前面，大的排在后面。
 * 如果只声明了MappingColumn未声明此注解该字段排序的权重默认为0。
 *
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportOrder {
    // order num
    int value();
}
