package com.jh.briefing.model;

import java.time.LocalDate;

/**
 * 最大日降雨量区域信息
 * Created by lxy on 2018/4/15.
 */
public class TrrmRegionMaxDay {
    private Long regionId;//区域编号
    private Double maxDayTrrm;//最大日均降雨量
    private String maxDayTrrmRegion;//最大日均降雨量区域名称
    private LocalDate maxDayTrrmDate;//最大日均降雨量日期（最大日降雨量最大的日期）
    private String rainStormLevel;//暴雨级别

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Double getMaxDayTrrm() {
        return maxDayTrrm;
    }

    public void setMaxDayTrrm(Double maxDayTrrm) {
        this.maxDayTrrm = maxDayTrrm;
    }

    public String getMaxDayTrrmRegion() {
        return maxDayTrrmRegion;
    }

    public void setMaxDayTrrmRegion(String maxDayTrrmRegion) {
        this.maxDayTrrmRegion = maxDayTrrmRegion;
    }

    public LocalDate getMaxDayTrrmDate() {
        return maxDayTrrmDate;
    }

    public void setMaxDayTrrmDate(LocalDate maxDayTrrmDate) {
        this.maxDayTrrmDate = maxDayTrrmDate;
    }

    public String getRainStormLevel() {
        return rainStormLevel;
    }

    public void setRainStormLevel(String rainStormLevel) {
        this.rainStormLevel = rainStormLevel;
    }
}
