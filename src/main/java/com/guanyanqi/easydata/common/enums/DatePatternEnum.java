package com.guanyanqi.easydata.common.enums;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public enum DatePatternEnum {
    DEFAULT("yyyy-MM-dd"),
    DEFAULT_HOUR("yyyy-MM-dd HH:mm:ss"),
    PATTERN1("yyyy/MM/dd"),
    PATTERN1_HOUR("yyyy/MM/dd HH:mm:ss");

    private final String patten;

    DatePatternEnum(String patten) {
        this.patten = patten;
    }

    public String getPatten() {
        return patten;
    }
}
