package com.jh.briefing.model;

import java.util.List;

/**
 * 简报数据载体
 * Created by lxy on 2018/4/13.
 */
public class ReporterData {
    /**
     * 简报基础信息
     */
    private String regionName; //生成简报的区域
    private String dateTimeSpan;//生成简报的日期范围
    private String cropsName;//作物名称
    private String growthPeriodName;//对应的物候期
    private Integer regionLevel;//区域级别
    private String remark;//备注信息

    /**
     * 地温统计信息
     */
    TtnStatistics ttnStatistics;

    /**
     * 周-月 雨量统计
     */
    TrrmStatistics trrmStatistics;

    /**
     * 作物病情
     */
    private List<DiseaseControlParam> diseaseControls;
    /**
     * 作物虫害
     */
    private List<DiseaseControlParam> bugDiseaseControls;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDateTimeSpan() {
        return dateTimeSpan;
    }

    public void setDateTimeSpan(String dateTimeSpan) {
        this.dateTimeSpan = dateTimeSpan;
    }

    public String getGrowthPeriodName() {
        return growthPeriodName;
    }

    public void setGrowthPeriodName(String growthPeriodName) {
        this.growthPeriodName = growthPeriodName;
    }

    public TtnStatistics getTtnStatistics() {
        return ttnStatistics;
    }

    public void setTtnStatistics(TtnStatistics ttnStatistics) {
        this.ttnStatistics = ttnStatistics;
    }

    public TrrmStatistics getTrrmStatistics() {
        return trrmStatistics;
    }

    public void setTrrmStatistics(TrrmStatistics trrmStatistics) {
        this.trrmStatistics = trrmStatistics;
    }

    public String getCropsName() {
        return cropsName;
    }

    public void setCropsName(String cropsName) {
        this.cropsName = cropsName;
    }

    public List<DiseaseControlParam> getDiseaseControls() {
        return diseaseControls;
    }

    public void setDiseaseControls(List<DiseaseControlParam> diseaseControls) {
        this.diseaseControls = diseaseControls;
    }

    public List<DiseaseControlParam> getBugDiseaseControls() {
        return bugDiseaseControls;
    }

    public void setBugDiseaseControls(List<DiseaseControlParam> bugDiseaseControls) {
        this.bugDiseaseControls = bugDiseaseControls;
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
