package com.jh.report.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: 有下级区域分布模板集数据Bean
 * 1.面积单位: 万亩
 *
 * @version <1> 2018-07-16 11:16 zhangshen: Created.
 */
public class DistributionCityData {

    private String regionName;// 区域名称，如洪山区

    /*private Date beginDate;// 监测开始时间: 2018-03-31

    private Date endDate;// 监测结束时间: 2017-10-01

    private String satelliteCN;// 卫星中文名: 高分一号卫星*/

    private String resolutionCN;// 高分一号16米

    private Date satelliteImageDate;// 卫星影像时间: 2018-03-28

    private Date satelliteImageDateLast;// 同期，同期卫星影像时间: 2017-03-28

    private String cropType;// 冬小麦 灌浆乳熟期

    private String cropName;// 作物名称: 冬小麦


    private Double endYearArea;// 种植面积: 892亩(万亩)

    private Double beginYearArea;// 种植面积: 835亩(万亩)

    private Integer trendFlag;// 1:增加,0:减少

    private Double area;// 增加或减少的面积: 57亩(万亩)

    private String areaPercentage;// 面积增幅百分比: 增幅7.51%


    private String regionNameListStr;//主要分布在襄阳、荆州、荆门、天门、黄冈、孝感、十堰等地区  <=5个

    private List<DistributionBaseData> distributionList;// 下级分布地区集合: 湖北省下级分布集合  所有

    private List<DistributionBaseData> reduceDisList;// 减少的分布地区集合 2个

    private List<DistributionBaseData> increaseDisList;// 增加的分布地区集合 2个


    private DistributionIncreaseData distributionIncreaseData;


    private Map<String, DistributionImage> imagesMap;//图片code

    private Integer isOverYearCrop;//是否时跨年作物：0:否，1:是

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /*public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSatelliteCN() {
        return satelliteCN;
    }

    public void setSatelliteCN(String satelliteCN) {
        this.satelliteCN = satelliteCN;
    }*/

    public Date getSatelliteImageDate() {
        return satelliteImageDate;
    }

    public void setSatelliteImageDate(Date satelliteImageDate) {
        this.satelliteImageDate = satelliteImageDate;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Double getEndYearArea() {
        return endYearArea;
    }

    public void setEndYearArea(Double endYearArea) {
        this.endYearArea = endYearArea;
    }

    public Double getBeginYearArea() {
        return beginYearArea;
    }

    public void setBeginYearArea(Double beginYearArea) {
        this.beginYearArea = beginYearArea;
    }

    public Integer getTrendFlag() {
        return trendFlag;
    }

    public void setTrendFlag(Integer trendFlag) {
        this.trendFlag = trendFlag;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getAreaPercentage() {
        return areaPercentage;
    }

    public void setAreaPercentage(String areaPercentage) {
        this.areaPercentage = areaPercentage;
    }

    public String getRegionNameListStr() {
        return regionNameListStr;
    }

    public void setRegionNameListStr(String regionNameListStr) {
        this.regionNameListStr = regionNameListStr;
    }

    public List<DistributionBaseData> getDistributionList() {
        return distributionList;
    }

    public void setDistributionList(List<DistributionBaseData> distributionList) {
        this.distributionList = distributionList;
    }

    public List<DistributionBaseData> getReduceDisList() {
        return reduceDisList;
    }

    public void setReduceDisList(List<DistributionBaseData> reduceDisList) {
        this.reduceDisList = reduceDisList;
    }

    public List<DistributionBaseData> getIncreaseDisList() {
        return increaseDisList;
    }

    public void setIncreaseDisList(List<DistributionBaseData> increaseDisList) {
        this.increaseDisList = increaseDisList;
    }

    public DistributionIncreaseData getDistributionIncreaseData() {
        return distributionIncreaseData;
    }

    public void setDistributionIncreaseData(DistributionIncreaseData distributionIncreaseData) {
        this.distributionIncreaseData = distributionIncreaseData;
    }

    public String getResolutionCN() {
        return resolutionCN;
    }

    public void setResolutionCN(String resolutionCN) {
        this.resolutionCN = resolutionCN;
    }

    public Map<String, DistributionImage> getImagesMap() {
        return imagesMap;
    }

    public void setImagesMap(Map<String, DistributionImage> imagesMap) {
        this.imagesMap = imagesMap;
    }

    public Date getSatelliteImageDateLast() {
        return satelliteImageDateLast;
    }

    public void setSatelliteImageDateLast(Date satelliteImageDateLast) {
        this.satelliteImageDateLast = satelliteImageDateLast;
    }

    public Integer getIsOverYearCrop() {
        return isOverYearCrop;
    }

    public void setIsOverYearCrop(Integer isOverYearCrop) {
        this.isOverYearCrop = isOverYearCrop;
    }
}
