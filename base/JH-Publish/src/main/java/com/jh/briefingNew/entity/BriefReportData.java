package com.jh.briefingNew.entity;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-17 wl: Created.
 */
public class BriefReportData {
    /**
     * 简报基础信息
     */
    private String regionName; //生成简报的区域
    private Integer growthId;//物候期编号
    private String growthPeriodName;//对应的物候期
    private String cropsName;//作物名称
    private Integer regionLevel;//区域级

    private String beginDate;//生成简报的日期范围(年)
    private String endDate;//生成简报的日期范围（月-日）

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

    private String comparedLastYear;//同比增减情况

    private String soilMoisture;//墒情评价

    private String disease;//易发病害
    private String InsectPests;//易发虫害

    private String remark;//备注信息

    List<String> ttnDateList;//地温统计图时间
    List<Double> ttnNowList;//当前地温编号
    List<Double> ttnHistoryList;//历史地温均值

    List<String> trmmDateList;//地温统计图时间
    List<Double> trmmNowList;//当前地温编号
    List<Double> trmmHistoryList;//历史地温均值

    private String ttnDate;
    private String ttnNow;
    private String ttnHistory;

    private Double maxTtn;//简报降雨统计图的y轴最大值设置
    private Double minTtn;//简报降雨统计图的y轴最小值设置


    private String trmmDate;
    private String trmmNow;
    private String trmmHistory;

    private Double maxTrmm;//简报降雨统计图的y轴最大值设置

    private String chinaName;

    private String regionCode;



    List<String> instructions;//物候期作物适宜情况说明，如是否利于拔节
    List<BriefingDiseaseAll> diseaseAllList;//病虫害及防治

    public Integer getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Integer growthId) {
        this.growthId = growthId;
    }

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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getComparedLastYear() {
        return comparedLastYear;
    }

    public void setComparedLastYear(String comparedLastYear) {
        this.comparedLastYear = comparedLastYear;
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

    public List<BriefingDiseaseAll> getDiseaseAllList() {
        return diseaseAllList;
    }

    public void setDiseaseAllList(List<BriefingDiseaseAll> diseaseAllList) {
        this.diseaseAllList = diseaseAllList;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getTtnDate() {
        return ttnDate;
    }

    public void setTtnDate(String ttnDate) {
        this.ttnDate = ttnDate;
    }

    public String getTtnNow() {
        return ttnNow;
    }

    public void setTtnNow(String ttnNow) {
        this.ttnNow = ttnNow;
    }

    public String getTtnHistory() {
        return ttnHistory;
    }

    public void setTtnHistory(String ttnHistory) {
        this.ttnHistory = ttnHistory;
    }

    public String getTrmmDate() {
        return trmmDate;
    }

    public void setTrmmDate(String trmmDate) {
        this.trmmDate = trmmDate;
    }

    public String getTrmmNow() {
        return trmmNow;
    }

    public void setTrmmNow(String trmmNow) {
        this.trmmNow = trmmNow;
    }

    public String getTrmmHistory() {
        return trmmHistory;
    }

    public void setTrmmHistory(String trmmHistory) {
        this.trmmHistory = trmmHistory;
    }

    public Double getMaxTrmm() {
        return maxTrmm;
    }

    public void setMaxTrmm(Double maxTrmm) {
        this.maxTrmm = maxTrmm;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public List<String> getTtnDateList() {
        return ttnDateList;
    }

    public void setTtnDateList(List<String> ttnDateList) {
        this.ttnDateList = ttnDateList;
    }

    public Double getMaxTtn() {
        return maxTtn;
    }

    public void setMaxTtn(Double maxTtn) {
        this.maxTtn = maxTtn;
    }

    public Double getMinTtn() {
        return minTtn;
    }

    public void setMinTtn(Double minTtn) {
        this.minTtn = minTtn;
    }

    public List<Double> getTtnNowList() {
        return ttnNowList;
    }

    public void setTtnNowList(List<Double> ttnNowList) {
        this.ttnNowList = ttnNowList;
    }

    public List<Double> getTtnHistoryList() {
        return ttnHistoryList;
    }

    public void setTtnHistoryList(List<Double> ttnHistoryList) {
        this.ttnHistoryList = ttnHistoryList;
    }

    public List<String> getTrmmDateList() {
        return trmmDateList;
    }

    public void setTrmmDateList(List<String> trmmDateList) {
        this.trmmDateList = trmmDateList;
    }

    public List<Double> getTrmmNowList() {
        return trmmNowList;
    }

    public void setTrmmNowList(List<Double> trmmNowList) {
        this.trmmNowList = trmmNowList;
    }

    public List<Double> getTrmmHistoryList() {
        return trmmHistoryList;
    }

    public void setTrmmHistoryList(List<Double> trmmHistoryList) {
        this.trmmHistoryList = trmmHistoryList;
    }
}
