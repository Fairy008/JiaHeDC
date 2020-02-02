package com.jh.dgy.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * description:作物分布与估产参数对象
 * @version <1> 2018-04-28 cxw: Created.
 */
public class DistributionAndYieldEntity {
    @ApiModelProperty(value="区域ID",required=true)
    private Long regionId;

    @ApiModelProperty(value="区域代码")
    private String regionCode;

    @ApiModelProperty(value="查询开始日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value="查询结束日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value="作物ID")
    private Integer cropId;

    @ApiModelProperty(value="作物代码")
    private String cropCode;

    @ApiModelProperty(value="分辨率：如50m,16m")
    private Integer resolution;

    @ApiModelProperty(value="分布日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dataTime ;


    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }
}
