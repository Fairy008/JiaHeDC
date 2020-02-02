package com.jh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * description:
 *
 * @version <1> 2018-05-07 wl: Created.
 */
public class DsYield extends BaseEntity {

    @ApiModelProperty(value = "数据主键ID")
    private Integer id;

    @ApiModelProperty(value = "区域主键ID")
    private Long regionId;

    @ApiModelProperty(value = "农作物ID")
    private Integer cropId;

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "精度")
    private Integer resolution;

    @ApiModelProperty(value = "生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;

    @ApiModelProperty(value = "数据源类型")
    private Integer dataType;

    @ApiModelProperty(value = "估产面积（亩）")
    private BigDecimal yieId;

    @ApiModelProperty(value = "发布状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "发布人名称")
    @IdTransform(type= CacheUtil.CACHE_ACCOUNT_TYPE,propName = "publisher")
    private String publisherName;

    @ApiModelProperty(value = "发布人")
    private String publisher;

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;//区域中文名

    private String regionCode;//区域Code

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//农作物名称

    @ApiModelProperty(value = "估产面积(亩)")
    private BigDecimal yield;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution")
    private String resolutionValue;//精度值

    private String publishStatusName;//发布状态

    private Integer productId;//任务Id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public BigDecimal getYieId() {
        return yieId;
    }

    public void setYieId(BigDecimal yieId) {
        this.yieId = yieId;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public String getResolutionValue() {
        return resolutionValue;
    }

    public void setResolutionValue(String resolutionValue) {
        this.resolutionValue = resolutionValue;
    }

    public String getPublishStatusName() {
        return publishStatusName;
    }

    public void setPublishStatusName(String publishStatusName) {
        this.publishStatusName = publishStatusName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }
}
