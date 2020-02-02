package com.jh.briefingNew.entity;

/**
 * description:
 *
 * @version <1> 2018-07-17 wl: Created.
 */
public class BriefingDiseaseAll {

    private Integer diseaseId; //主键

    private Integer growthId;//作物生育期

    private String diseaseName; //虫害名称

    private String diseaseMeasure;//虫害防治措施

    private String diseaseRemark;//虫害防治备注

    private String diseaseImg;//病虫害图片

    private Integer cropsId;//作物ID

    private Long regionId;//区域ID

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

    public String getDiseaseImg() {
        return diseaseImg;
    }

    public void setDiseaseImg(String diseaseImg) {
        this.diseaseImg = diseaseImg;
    }
}
