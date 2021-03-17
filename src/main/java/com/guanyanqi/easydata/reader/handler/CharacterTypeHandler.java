package com.guanyanqi.easydata.reader.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class CharacterTypeHandler implements TypeHandler<Character> {

    private static final Logger logger = LoggerFactory.getLogger(CharacterTypeHandler.class);

    @Override
    public Character handle(String sourceString) {
        if (StringUtils.isNotBlank(sourceString) && sourceString.length() == 1) {
            logger.error("输入的字符串：{} 不支持转换为Character",sourceString );
            throw new RuntimeException("输入的字符串：" + sourceString + " 不支持转换为Character");
        }
        return sourceString.charAt(0);
    }

    @Override
    public String getType() {
        return "java.lang.Character";
    }
}
