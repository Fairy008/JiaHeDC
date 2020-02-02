package com.jh.briefingNew.model;


import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-16 wl: Created.
 */
public class ReportData {
    /**
     * 简报基础信息
     */
    private String regionName; //生成简报的区域
    private String growthPeriodName;//对应的物候期
    private String cropsName;//作物名称
    private Integer regionLevel;//区域级

    private String dateTimeYear;//生成简报的日期范围(年)
    private String dateTimeDay;//生成简报的日期范围（月-日）

    private Double ttnAvgMin;//日均地温最低值
    private String ttnAvgMinRegionName;//日均地温最低值区域名
    private Double ttnAvgMax;//日均地温最高值
    private String ttnAvgMaxRegionName;//日均地温最高值区域名

    private Double ttnTotalMin;//有效积温最低值
    private String ttnTotalMinRegionName;//有效积温最低值区域名
    private Double ttnTotalMax;//有效积温最高值
    private String ttnTotalMaxRegionName;//有效积温最高值区域名

    private Double trmmTotalMin;//降雨总量最低值
    private String trmmTotalMinRegionName;//降雨总量最低值区域名
    private Double trmmTotalMax;//降雨总量最高值
    private String trmmTotalMaxRegionName;//降雨总量最高值区域名

    private Double trmmRise;//降雨同比增加最多
    private String trmmRiseRegionName;//降雨同比增加最多区域名
    private Double trmmFall;//降雨同比减少最多
    private String trmmFallRegionName;//降雨同比减少最多区域名

    private String soilMoisture;//墒情评价

    private String disease;//易发病害
    private String InsectPests;//易发虫害

    private String remark;//备注信息

    List<TrmmDatas> trmmDatasNow;//降雨
    List<chartDatas> chartDatasNow;//地温
    List<TrmmDatas> trmmDatasHistory;//降雨历史
    List<chartDatas> chartDatasHistory;//地温历史

    List<BriefingDiseaseAllParam> briefingDiseaseAllParamList;//病虫害及防治


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getGrowthPeriodName() {
        return growthPeriodName;
    }

    public void setGrowthPeriodName(String growthPeriodName) {
        this.growthPeriodName = growthPeriodName;
    }

    public String getCropsName() {
        return cropsName;
    }

    public void setCropsName(String cropsName) {
        this.cropsName = cropsName;
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public String getDateTimeYear() {
        return dateTimeYear;
    }

    public void setDateTimeYear(String dateTimeYear) {
        this.dateTimeYear = dateTimeYear;
    }

    public String getDateTimeDay() {
        return dateTimeDay;
    }

    public void setDateTimeDay(String dateTimeDay) {
        this.dateTimeDay = dateTimeDay;
    }

    public Double getTtnAvgMin() {
        return ttnAvgMin;
    }

    public void setTtnAvgMin(Double ttnAvgMin) {
        this.ttnAvgMin = ttnAvgMin;
    }

    public String getTtnAvgMinRegionName() {
        return ttnAvgMinRegionName;
    }

    public void setTtnAvgMinRegionName(String ttnAvgMinRegionName) {
        this.ttnAvgMinRegionName = ttnAvgMinRegionName;
    }

    public Double getTtnAvgMax() {
        return ttnAvgMax;
    }

    public void setTtnAvgMax(Double ttnAvgMax) {
        this.ttnAvgMax = ttnAvgMax;
    }

    public String getTtnAvgMaxRegionName() {
        return ttnAvgMaxRegionName;
    }

    public void setTtnAvgMaxRegionName(String ttnAvgMaxRegionName) {
        this.ttnAvgMaxRegionName = ttnAvgMaxRegionName;
    }

    public Double getTtnTotalMin() {
        return ttnTotalMin;
    }

    public void setTtnTotalMin(Double ttnTotalMin) {
        this.ttnTotalMin = ttnTotalMin;
    }

    public String getTtnTotalMinRegionName() {
        return ttnTotalMinRegionName;
    }

    public void setTtnTotalMinRegionName(String ttnTotalMinRegionName) {
        this.ttnTotalMinRegionName = ttnTotalMinRegionName;
    }

    public Double getTtnTotalMax() {
        return ttnTotalMax;
    }

    public void setTtnTotalMax(Double ttnTotalMax) {
        this.ttnTotalMax = ttnTotalMax;
    }

    public String getTtnTotalMaxRegionName() {
        return ttnTotalMaxRegionName;
    }

    public void setTtnTotalMaxRegionName(String ttnTotalMaxRegionName) {
        this.ttnTotalMaxRegionName = ttnTotalMaxRegionName;
    }

    public Double getTrmmTotalMin() {
        return trmmTotalMin;
    }

    public void setTrmmTotalMin(Double trmmTotalMin) {
        this.trmmTotalMin = trmmTotalMin;
    }

    public String getTrmmTotalMinRegionName() {
        return trmmTotalMinRegionName;
    }

    public void setTrmmTotalMinRegionName(String trmmTotalMinRegionName) {
        this.trmmTotalMinRegionName = trmmTotalMinRegionName;
    }

    public Double getTrmmTotalMax() {
        return trmmTotalMax;
    }

    public void setTrmmTotalMax(Double trmmTotalMax) {
        this.trmmTotalMax = trmmTotalMax;
    }

    public String getTrmmTotalMaxRegionName() {
        return trmmTotalMaxRegionName;
    }

    public void setTrmmTotalMaxRegionName(String trmmTotalMaxRegionName) {
        this.trmmTotalMaxRegionName = trmmTotalMaxRegionName;
    }

    public Double getTrmmRise() {
        return trmmRise;
    }

    public void setTrmmRise(Double trmmRise) {
        this.trmmRise = trmmRise;
    }

    public String getTrmmRiseRegionName() {
        return trmmRiseRegionName;
    }

    public void setTrmmRiseRegionName(String trmmRiseRegionName) {
        this.trmmRiseRegionName = trmmRiseRegionName;
    }

    public Double getTrmmFall() {
        return trmmFall;
    }

    public void setTrmmFall(Double trmmFall) {
        this.trmmFall = trmmFall;
    }

    public String getTrmmFallRegionName() {
        return trmmFallRegionName;
    }

    public void setTrmmFallRegionName(String trmmFallRegionName) {
        this.trmmFallRegionName = trmmFallRegionName;
    }

    public String getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(String soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getInsectPests() {
        return InsectPests;
    }

    public void setInsectPests(String insectPests) {
        InsectPests = insectPests;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<TrmmDatas> getTrmmDatasNow() {
        return trmmDatasNow;
    }

    public void setTrmmDatasNow(List<TrmmDatas> trmmDatasNow) {
        this.trmmDatasNow = trmmDatasNow;
    }

    public List<chartDatas> getChartDatasNow() {
        return chartDatasNow;
    }

    public void setChartDatasNow(List<chartDatas> chartDatasNow) {
        this.chartDatasNow = chartDatasNow;
    }

    public List<TrmmDatas> getTrmmDatasHistory() {
        return trmmDatasHistory;
    }

    public void setTrmmDatasHistory(List<TrmmDatas> trmmDatasHistory) {
        this.trmmDatasHistory = trmmDatasHistory;
    }

    public List<chartDatas> getChartDatasHistory() {
        return chartDatasHistory;
    }

    public void setChartDatasHistory(List<chartDatas> chartDatasHistory) {
        this.chartDatasHistory = chartDatasHistory;
    }

    public List<BriefingDiseaseAllParam> getBriefingDiseaseAllParamList() {
        return briefingDiseaseAllParamList;
    }

    public void setBriefingDiseaseAllParamList(List<BriefingDiseaseAllParam> briefingDiseaseAllParamList) {
        this.briefingDiseaseAllParamList = briefingDiseaseAllParamList;
    }
}
