package com.jh.briefing.entity;

import java.time.LocalDateTime;

/**
 * 病虫防治措施
 */
public class DiseaseControlManager {
    private Integer diseaseId; //主键

    private Integer growthId; //生育周期编号

    private String diseaseName; //作物生病名称

    private String diseaseMeasure; //作物生病防治措施

    private String diseaseRemark; //作物生病备注

    private Integer cropsId;//作物ID

    private Long regionId;//区域ID

    private Integer diseaseType;//防治病虫害类型 （1：病害，2：虫害）

    private LocalDateTime createTime;//创建时间

    private LocalDateTime modifyTime;//修改时间

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

    public Integer getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(Integer diseaseType) {
        this.diseaseType = diseaseType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
}