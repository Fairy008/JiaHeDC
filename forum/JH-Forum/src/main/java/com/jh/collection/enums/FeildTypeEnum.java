package com.jh.collection.enums;

/**
 * Created by xy on 2018/12/3.
 */
public enum FeildTypeEnum {

    CHARACTER ("character ","文字"),
    INTEGER("integer","整数"),
    TEXT("text","大文本"),
    TIMESTAMP ("timestamp ","日期和时间"),
    DOUBLE ("double ","小数(两位)");

    FeildTypeEnum(String value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    private String value;
    private String describe;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
