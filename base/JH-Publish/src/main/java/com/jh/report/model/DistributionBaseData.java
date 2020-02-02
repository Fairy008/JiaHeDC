package com.jh.report.model;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-07-16 17:38 zhangshen: Created.
 */
public class DistributionBaseData {

    private String regionName;// 区域名称，如洪山区

    private Double endYearArea;// 种植面积: 892亩(万亩)

    private Double beginYearArea;// 种植面积: 835亩(万亩)

    private Integer trendFlag;// 1:增加,0:减少

    private Double area;// 增加或减少的面积: 57亩(万亩)

    private String areaPercentage;// 面积增幅百分比: 增幅7.51%

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Double getEndYearArea() {
        return endYearArea;
    }

    public void setEndYearArea(Double endYearArea) {
        this.endYearArea = endYearArea;
    }

    public Double getBeginYearArea() {
        return beginYearArea;
    }

    public void setBeginYearArea(Double beginYearArea) {
        this.beginYearArea = beginYearArea;
    }

    public Integer getTrendFlag() {
        return trendFlag;
    }

    public void setTrendFlag(Integer trendFlag) {
        this.trendFlag = trendFlag;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getAreaPercentage() {
        return areaPercentage;
    }

    public void setAreaPercentage(String areaPercentage) {
        this.areaPercentage = areaPercentage;
    }
}
