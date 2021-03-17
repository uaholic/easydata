package com.guanyanqi.easydata.common.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class DateUtil {
    public static Date dateParse(String dateStr, String pattern) {
        FastDateFormat instance = FastDateFormat.getInstance(pattern);
        try {
            return instance.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("date format exception", e);
        }
    }

    public static String dateFormat(Date date, String pattern) {
        FastDateFormat instance = FastDateFormat.getInstance(pattern);
        return instance.format(date);
    }

}
