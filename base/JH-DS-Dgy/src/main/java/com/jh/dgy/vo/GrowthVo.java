/**
* 用于定义两个服务查询的结果集：
* 服务1 ：查询当前日期及同期数据环比：
* 服务2 ：查询当前日期截止的三年数据与十年均值数据：
*
* @version <1> 2017-11-17 Hayden:Created.
*/
package com.jh.dgy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * description:长势返回对象
 * @version <1> 2018-04-27 cxw: Created.
 */
public class GrowthVo {

    private Long regionId; //区域ID

    private String regionName; //区域名

    private Float value; //本期数据集值

    private Float lastValue; //上一期数据集值

    private Float percent;  //本期相比上一期增减百分比

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTime;   //日期

    private String monthAndDay;  //月日作为x轴

    private Integer year;  //年

    private String cropName;  //作物名称

    private String regionCode;//区域编码

    private Integer cropId;//作物id

    private Float lowest;//长势-很差(平方公里)

    private Float lower;//长势-差(平方公里)

    private Float low;//长势-较差(平方公里)

    private Float normal;//长势-中等(平方公里)

    private Float high;//长势-较好(平方公里)

    private Float higher;//长势-好(平方公里)

    private Float highest;//长势-很好(平方公里)

    private Float lowestPercent;//长势-很差(百分比)

    private Float lowerPercent;//长势-差(百分比)

    private Float lowPercent;//长势-较差(百分比)

    private Float normalPercent;//长势-中等(百分比)

    private Float highPercent;//长势-较好(百分比)

    private Float higherPercent;//长势-好(百分比)

    private Float highestPercent;//长势-很好(百分比)

    private Integer resolution;//精度Id

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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getLastValue() {
        return lastValue;
    }

    public void setLastValue(Float lastValue) {
        this.lastValue = lastValue;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }

    public String getMonthAndDay() {
        return monthAndDay;
    }

    public void setMonthAndDay(String monthAndDay) {
        this.monthAndDay = monthAndDay;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Float getLowest() {
        return lowest;
    }

    public void setLowest(Float lowest) {
        this.lowest = lowest;
    }

    public Float getLower() {
        return lower;
    }

    public void setLower(Float lower) {
        this.lower = lower;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getNormal() {
        return normal;
    }

    public void setNormal(Float normal) {
        this.normal = normal;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Float getHigher() {
        return higher;
    }

    public void setHigher(Float higher) {
        this.higher = higher;
    }

    public Float getHighest() {
        return highest;
    }

    public void setHighest(Float highest) {
        this.highest = highest;
    }

    public Float getLowestPercent() {
        return lowestPercent;
    }

    public void setLowestPercent(Float lowestPercent) {
        this.lowestPercent = lowestPercent;
    }

    public Float getLowerPercent() {
        return lowerPercent;
    }

    public void setLowerPercent(Float lowerPercent) {
        this.lowerPercent = lowerPercent;
    }

    public Float getLowPercent() {
        return lowPercent;
    }

    public void setLowPercent(Float lowPercent) {
        this.lowPercent = lowPercent;
    }

    public Float getNormalPercent() {
        return normalPercent;
    }

    public void setNormalPercent(Float normalPercent) {
        this.normalPercent = normalPercent;
    }

    public Float getHighPercent() {
        return highPercent;
    }

    public void setHighPercent(Float highPercent) {
        this.highPercent = highPercent;
    }

    public Float getHigherPercent() {
        return higherPercent;
    }

    public void setHigherPercent(Float higherPercent) {
        this.higherPercent = higherPercent;
    }

    public Float getHighestPercent() {
        return highestPercent;
    }

    public void setHighestPercent(Float highestPercent) {
        this.highestPercent = highestPercent;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }
}