package com.guanyanqi.easydata.reader.config;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class FileInfoConfig {
    /**
     * 文件路径 必传
     */
    private final String filePath;
    /**
     * 内容开始行数 默认从第一行开始
     */
    private final Long startLine;
    /**
     * 内容结束行数 默认到最后一行结束
     */
    private final Long endLine;
    /**
     * 文件类型
     */
    private final String type;
    /**
     * 字符集 默认 utf-8
     */
    private final String charSet;

    /**
     * 备注
     */
    private final String remark;

    private FileInfoConfig(Builder builder) {
        this.filePath = builder.filePath;
        this.startLine = builder.startLine;
        this.endLine = builder.endLine;
        this.type = builder.type;
        this.charSet = builder.charSet;
        this.remark=builder.remark;
    }

    public static class Builder {
        private final String filePath;
        private Long startLine;
        private Long endLine;
        private String type;
        private String charSet = "UTF-8";
        private String remark;

        public Builder(String filePath) {
            this.filePath = filePath;
        }

        public Builder startLine(Long startLine) {
            this.startLine = startLine;
            return this;
        }

        public Builder endLine(Long endLine) {
            this.endLine = endLine;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder charSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public Builder remark(String remark) {
            this.remark = remark;
            return this;
        }

        public FileInfoConfig build() {
            return new FileInfoConfig(this);
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public Long getStartLine() {
        return startLine;
    }

    public Long getEndLine() {
        return endLine;
    }

    public String getType() {
        return type;
    }

    public String getCharSet() {
        return charSet;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return "FileInfoConfig{" +
                "filePath='" + filePath + '\'' +
                ", startLine=" + startLine +
                ", endLine=" + endLine +
                ", type='" + type + '\'' +
                ", charSet='" + charSet + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
