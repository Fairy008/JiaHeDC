package com.jh.briefing.entity;

/**
 * 土壤墒情
 */
public class SoilMoisture {
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
        this.soidName = soidName == null ? null : soidName.trim();
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