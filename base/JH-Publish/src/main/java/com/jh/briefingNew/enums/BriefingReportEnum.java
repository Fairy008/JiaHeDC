package com.jh.briefingNew.enums;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
public enum BriefingReportEnum {
    IS_HISTORY("IS_HISTORY",1,"历史数据"),
    NO_HISTORY("NO_HISTORY",0,"当前数据"),
    DISEASE("DISEASE",1,"病害"),
    BUG_TYPE("BUG_TYPE",2,"虫害"),
    TEXT_TEMPLATE("TEXT_TEMPLATE",2501,"文本类型"),
    CHART_TEMPLATE("CHART_TEMPLATE",2502,"图表类型"),
    IS_WINTER_CROP("IS_WINTER_CROP",1,"是冬季作物"),
    NO_WINTER_CROP("NO_WINTER_CROP",0,"非冬季作物");

    private String key ;
    private Integer value;
    private String text;

    private BriefingReportEnum(String key, Integer value, String text){
        this.key = key;
        this.value = value;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
