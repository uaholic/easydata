package com.guanyanqi.easydata.reader.handler;

import java.util.Date;




import com.guanyanqi.easydata.common.utils.DateUtil;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class DateTypeHandler implements TypeHandler<Date> {

    @Override
    public Date handle(String sourceString) {
        return DateUtil.dateParse(sourceString, "yyyy-MM-dd");
    }

    @Override
    public String getType() {
        return "java.util.Date";
    }

}
