package com.jh.briefing.model;


import com.jh.entity.PageEntity;

/**
 * 作物虫害类 查询参数类
 */
public class BugDiseaseControlParam extends PageEntity{
    private Integer bugId; //主键

    private Integer growthId;//作物生育期

    private String growthName;//生育期名称

    private Integer cropsId;//作物ID

    private String cropsName;//作物名称

    private Long regionId;//区域ID

    private String regionName;//区域名称

    private String bugDiseaseName; //虫害名称

    private String bugDiseaseMeasure;//虫害防治措施

    private String bugDiseaseRemark;//虫害防治备注

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

    public String getGrowthName() {
        return growthName;
    }

    public void setGrowthName(String growthName) {
        this.growthName = growthName;
    }

    public Integer getCropsId() {
        return cropsId;
    }

    public void setCropsId(Integer cropsId) {
        this.cropsId = cropsId;
    }

    public String getCropsName() {
        return cropsName;
    }

    public void setCropsName(String cropsName) {
        this.cropsName = cropsName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBugDiseaseName() {
        return bugDiseaseName;
    }

    public void setBugDiseaseName(String bugDiseaseName) {
        this.bugDiseaseName = bugDiseaseName;
    }

    public String getBugDiseaseMeasure() {
        return bugDiseaseMeasure;
    }

    public void setBugDiseaseMeasure(String bugDiseaseMeasure) {
        this.bugDiseaseMeasure = bugDiseaseMeasure;
    }

    public String getBugDiseaseRemark() {
        return bugDiseaseRemark;
    }

    public void setBugDiseaseRemark(String bugDiseaseRemark) {
        this.bugDiseaseRemark = bugDiseaseRemark;
    }
}