package com.jh.report.model;

/**
 * Description: 种植面积增加的产区 部分
 * 1.
 *
 * @version <1> 2018-07-16 11:28 zhangshen: Created.
 */
public class DistributionIncreaseData {

    private String regionNames;//襄阳市、荆州市、荆门市

    private String areaPercentage;//其种植面积总和约占湖北省的62.17%

    private String otherAreaPercentage;//其它地区面积占比约为37.83%

    public String getRegionNames() {
        return regionNames;
    }

    public void setRegionNames(String regionNames) {
        this.regionNames = regionNames;
    }

    public String getAreaPercentage() {
        return areaPercentage;
    }

    public void setAreaPercentage(String areaPercentage) {
        this.areaPercentage = areaPercentage;
    }

    public String getOtherAreaPercentage() {
        return otherAreaPercentage;
    }

    public void setOtherAreaPercentage(String otherAreaPercentage) {
        this.otherAreaPercentage = otherAreaPercentage;
    }
}
