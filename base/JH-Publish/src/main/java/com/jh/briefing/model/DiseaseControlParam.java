package com.jh.briefing.model;


import com.jh.entity.PageEntity;

import java.time.LocalDateTime;

/**
 * 病虫防治措施
 */
public class DiseaseControlParam extends PageEntity {
    private Integer diseaseId; //主键

    private Integer growthId; //生育周期编号

    private String growthName;//生育期名称

    private Integer cropsId;//作物ID

    private String cropsName;//作物名称

    private Long regionId;//区域ID

    private String regionName;//区域名称

    private String diseaseName; //作物病情名称

    private String diseaseMeasure; //作物病情防治措施

    private String diseaseRemark; //作物生病备注

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