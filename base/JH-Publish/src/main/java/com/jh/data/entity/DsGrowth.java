package com.jh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * description:长势
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@ApiModel(value = "长势明细对象")
public class DsGrowth extends BaseEntity {
    @ApiModelProperty(value = "数据主键ID")
    private Integer id;

    @ApiModelProperty(value = "区域主键ID")
    private Long regionId;

    @ApiModelProperty(value = "农作物ID")
    private Integer cropId;

    @ApiModelProperty(value = "精度")
    private Integer resolution;

//    @ApiModelProperty(value = "长势均值1km")
//    private BigDecimal growth;
//
//    @ApiModelProperty(value = "长势最大值1km")
//    private BigDecimal growthMax;
//
//    @ApiModelProperty(value = "长势最小值1km")
//    private BigDecimal growthMin;
//
//    @ApiModelProperty(value = "长势标准差1km")
//    private BigDecimal growthStddev;

    @ApiModelProperty(value = "异常偏低")
    private BigDecimal lowest;

    @ApiModelProperty(value = "明显偏低")
    private BigDecimal lower;

    @ApiModelProperty(value = "偏低")
    private BigDecimal low;

    @ApiModelProperty(value = "正常（持平）")
    private BigDecimal normal;

    @ApiModelProperty(value = "偏高")
    private BigDecimal high;

    @ApiModelProperty(value = "明显偏高")
    private BigDecimal higher ;

    @ApiModelProperty(value = "异常偏高")
    private BigDecimal highest;



    @ApiModelProperty(value = "生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTime;

    @ApiModelProperty(value = "数据源类型")
    private Integer dataType;

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

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String regionCode;//区域Code

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//农作物名称

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution")
    private String resolutionValue;//精度

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "publishStatus")
    private  String publishStatusName;//发布状态

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

    public BigDecimal getLowest() {
        return lowest;
    }

    public void setLowest(BigDecimal lowest) {
        this.lowest = lowest;
    }

    public BigDecimal getLower() {
        return lower;
    }

    public void setLower(BigDecimal lower) {
        this.lower = lower;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getNormal() {
        return normal;
    }

    public void setNormal(BigDecimal normal) {
        this.normal = normal;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getHigher() {
        return higher;
    }

    public void setHigher(BigDecimal higher) {
        this.higher = higher;
    }

    public BigDecimal getHighest() {
        return highest;
    }

    public void setHighest(BigDecimal highest) {
        this.highest = highest;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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
}
