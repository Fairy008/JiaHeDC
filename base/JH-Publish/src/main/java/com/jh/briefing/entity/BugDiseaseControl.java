package com.jh.briefing.entity;

/**
 * 作物虫害类
 */
public class BugDiseaseControl {
    private Integer bugId; //主键

    private Integer growthId;//作物生育期

    private String bugDiseaseName; //虫害名称

    private String bugDiseaseMeasure;//虫害防治措施

    private String bugDiseaseRemark;//虫害防治备注

    private Integer cropsId;//作物ID

    private Long regionId;//区域ID

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public Integer getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Integer growthId) {
        this.growthId = growthId;
    }

    public String getBugDiseaseName() {
        return bugDiseaseName;
    }

    public void setBugDiseaseName(String bugDiseaseName) {
        this.bugDiseaseName = bugDiseaseName == null ? null : bugDiseaseName.trim();
    }

    public String getBugDiseaseMeasure() {
        return bugDiseaseMeasure;
    }

    public void setBugDiseaseMeasure(String bugDiseaseMeasure) {
        this.bugDiseaseMeasure = bugDiseaseMeasure == null ? null : bugDiseaseMeasure.trim();
    }

    public String getBugDiseaseRemark() {
        return bugDiseaseRemark;
    }

    public void setBugDiseaseRemark(String bugDiseaseRemark) {
        this.bugDiseaseRemark = bugDiseaseRemark == null ? null : bugDiseaseRemark.trim();
    }

    public Integer getCropsId() {
        return cropsId;
    }

    public void setCropsId(Integer cropsId) {
        this.cropsId = cropsId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}