package com.jh.briefingNew.model;



/**
 * 作物虫害类 查询参数类
 */
public class BriefingDiseaseAllParam {
    private Integer diseaseId; //主键

    private Integer growthId;//作物生育期

    private String growthName;//生育期名称

    private Integer cropsId;//作物ID

    private String cropsName;//作物名称

    private Long regionId;//区域ID

    private String regionName;//区域名称

    private String diseaseName; //虫害名称

    private String diseaseMeasure;//虫害防治措施

    private String diseaseRemark;//虫害防治备注

    public Integer getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
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

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseMeasure() {
        return diseaseMeasure;
    }

    public void setDiseaseMeasure(String diseaseMeasure) {
        this.diseaseMeasure = diseaseMeasure;
    }

    public String getDiseaseRemark() {
        return diseaseRemark;
    }

    public void setDiseaseRemark(String diseaseRemark) {
        this.diseaseRemark = diseaseRemark;
    }
}