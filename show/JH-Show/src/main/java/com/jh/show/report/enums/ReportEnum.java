package com.jh.show.report.enums;

/**
 * description:报告分析模块枚举
 *
 * @version <1> 2018-06-24 wl: Created.
 */
public enum ReportEnum {
    PUBLISHED("PUBLISHED",2201,"已发布"),
    WAIT_PUBLISH("WAIT_PUBLISH",2202,"待发布"),
    DS_AREA("DS_AREA",1801,"分布"),
    CUSTOMIZED_AREA("CUSTOMIZED",2401,"用户初次登录定制的区域"),
    DRAW_AREA("DRAW_AREA",2402,"用户绘制的区域");

    private String key ;
    private Integer value;
    private String text;

    private ReportEnum(String key, Integer value, String text){
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
