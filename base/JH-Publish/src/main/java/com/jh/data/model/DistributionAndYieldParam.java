package com.jh.data.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * 1.用于定义分布或估产服务查询的结果集
 *
 * @version <1> 2018-05-10 16:35 zhangshen: Created.
 */
public class DistributionAndYieldParam {
    private String regionFullName;//区域全名称，如湖北省武汉市洪山区
    private String regionName;//区域名称，如武汉市
    private Long regionId;//区域主键
    private String cropName;//作物名称
    private String createDate;//生成日期
    private Integer year;//年
    private String beginMonthDay;//12月25日
    private String endMonthDay;//12月31日
    private String trend;//增加或减少越势
    private List<DistributionAndYieldDatas> growAreaList = new LinkedList<DistributionAndYieldDatas>();//较上年增长区域
    private List<DistributionAndYieldDatas> reduceAreaList = new LinkedList<DistributionAndYieldDatas>();//较上年减少区域
    private List<DistributionAndYieldDatas> threeAreaList = new LinkedList<DistributionAndYieldDatas>();//排名前三的子区域
    private List<DistributionAndYieldDatas> threeYearList = new LinkedList<DistributionAndYieldDatas>();//查询区域三年数据,如中国
    private List<String[]> threeYearCompareList = new LinkedList<String[]>();//三年数据占比
    private List<DistributionAndYieldDatas> dataList = new LinkedList<DistributionAndYieldDatas>();//分布或估产数据集合
    private List<DistributionAndYieldImages> imagesList = new LinkedList<DistributionAndYieldImages>();//分布或估产图片集合

    public String getRegionFullName() {
        return regionFullName;
    }

    public void setRegionFullName(String regionFullName) {
        this.regionFullName = regionFullName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public List<DistributionAndYieldDatas> getGrowAreaList() {
        return growAreaList;
    }

    public void setGrowAreaList(List<DistributionAndYieldDatas> growAreaList) {
        this.growAreaList = growAreaList;
    }

    public List<DistributionAndYieldDatas> getReduceAreaList() {
        return reduceAreaList;
    }

    public void setReduceAreaList(List<DistributionAndYieldDatas> reduceAreaList) {
        this.reduceAreaList = reduceAreaList;
    }

    public List<DistributionAndYieldDatas> getDataList() {
        return dataList;
    }

    public void setDataList(List<DistributionAndYieldDatas> dataList) {
        this.dataList = dataList;
    }

    public List<DistributionAndYieldImages> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<DistributionAndYieldImages> imagesList) {
        this.imagesList = imagesList;
    }

    public List<DistributionAndYieldDatas> getThreeAreaList() {
        return threeAreaList;
    }

    public void setThreeAreaList(List<DistributionAndYieldDatas> threeAreaList) {
        this.threeAreaList = threeAreaList;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public List<DistributionAndYieldDatas> getThreeYearList() {
        return threeYearList;
    }

    public void setThreeYearList(List<DistributionAndYieldDatas> threeYearList) {
        this.threeYearList = threeYearList;
    }

    public List<String[]> getThreeYearCompareList() {
        return threeYearCompareList;
    }

    public void setThreeYearCompareList(List<String[]> threeYearCompareList) {
        this.threeYearCompareList = threeYearCompareList;
    }

    public String getBeginMonthDay() {
        return beginMonthDay;
    }

    public void setBeginMonthDay(String beginMonthDay) {
        this.beginMonthDay = beginMonthDay;
    }

    public String getEndMonthDay() {
        return endMonthDay;
    }

    public void setEndMonthDay(String endMonthDay) {
        this.endMonthDay = endMonthDay;
    }
}
