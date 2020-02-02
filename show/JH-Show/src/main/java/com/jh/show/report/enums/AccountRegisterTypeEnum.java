package com.jh.show.report.enums;

/**
 * description:报告分析模块枚举
 *
 * @version <1> 2018-06-24 wl: Created.
 */
public enum AccountRegisterTypeEnum {
    RegisterType_WX("RegisterType_WX",1901,"微信端"),
    RegisterType_WEB("RegisterType_WEB",1902,"网页端");

    private String key ;
    private Integer value;
    private String text;

    private AccountRegisterTypeEnum(String key, Integer value, String text){
        this.key = key;
        this.value = value;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
