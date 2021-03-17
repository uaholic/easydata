package com.guanyanqi.easydata.reader.source;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public enum SourceTypeEnum {

    TEXT("text", "文本类"),
    CSV_TEXT("csv", "csv文本类");

    private String type;
    private String desc;

    SourceTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
