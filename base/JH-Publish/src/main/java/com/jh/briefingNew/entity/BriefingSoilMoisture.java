package com.jh.briefingNew.entity;

/**
 * description:
 *
 * @version <1> 2018-07-17 wl: Created.
 */
public class BriefingSoilMoisture {

    private Integer soidId; //墒情主键

    private String soidName; //墒情名称

    private Integer avgPercentageStart; //距平均比开始

    private Integer avgPercentageEnd; //距平均比结束


    public Integer getSoidId() {
        return soidId;
    }

    public void setSoidId(Integer soidId) {
        this.soidId = soidId;
    }

    public String getSoidName() {
        return soidName;
    }

    public void setSoidName(String soidName) {
        this.soidName = soidName;
    }

    public Integer getAvgPercentageStart() {
        return avgPercentageStart;
    }

    public void setAvgPercentageStart(Integer avgPercentageStart) {
        this.avgPercentageStart = avgPercentageStart;
    }

    public Integer getAvgPercentageEnd() {
        return avgPercentageEnd;
    }

    public void setAvgPercentageEnd(Integer avgPercentageEnd) {
        this.avgPercentageEnd = avgPercentageEnd;
    }
}
