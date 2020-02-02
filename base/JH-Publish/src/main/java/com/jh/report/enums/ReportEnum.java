package com.jh.report.enums;

/*
* 用于report返回消息码制定
* @version <1> 2017-12-20 cxj : Created.
*/
public enum ReportEnum {
    REPORT_TEMPLATE_AREA_CITY("distributionCity","市级/省级(有logo)模板"),
    REPORT_TEMPLATE_AREA_COUNTY("distributionCounty","区县级(有logo)模板"),
    REPORT_TEMPLATE_AREA_CITY_no_logo("distributionCityNoLogo","市级/省级(无logo)模板"),
    REPORT_TEMPLATE_AREA_COUNTY_no_logo("distributionCountyNoLogo","区县级(无logo)模板");

    private String key;
    private String value;

    private ReportEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
