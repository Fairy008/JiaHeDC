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
import java.util.List;

/**
 * description:作物分布与估产返回对象
 * @version <1> 2018-04-27 cxw: Created.
 */
public class DistributionAndYieldVo {

    private Long regionId; //区域ID

    private String regionName; //区域名

    private Float value; //本期数据集值

    private Float lastValue; //上一期数据集值

    private Float percent;  //本期相比上一期增减百分比

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTime;   //日期

    private Integer year;  //年

    private String cropName;  //作物名称

    private String regionCode;//区域编码
    
    private String regionParent;//上级区域编码
    
    private Float percentAge; //在所有同级区域中的占比

    private Integer cropId; //作物ID

    private Integer resolution;//精度Id

    
    public Float getPercentAge() {
		return percentAge;
	}

	public void setPercentAge(Float percentAge) {
		this.percentAge = percentAge;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
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

	public String getRegionParent() {
		return regionParent;
	}

	public void setRegionParent(String regionParent) {
		this.regionParent = regionParent;
	}

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }
}