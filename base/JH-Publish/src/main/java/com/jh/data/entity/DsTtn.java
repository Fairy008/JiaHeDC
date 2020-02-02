package com.jh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * description:地温、干旱、降雨 明细对象
 *
 * @version <1> 2018-05-07 wl: Created.
 */
public class DsTtn extends BaseEntity {
    @ApiModelProperty(value = "数据主键ID")
    private Integer id;

    @ApiModelProperty(value = "区域主键ID")
    private Long regionId;

    @ApiModelProperty(value = "农作物ID")
    private Integer cropId;

    @ApiModelProperty(value = "区域干旱均值1km")
    private BigDecimal nddi;

    @ApiModelProperty(value = "区域干旱最小值1km")
    private BigDecimal nddiMin;

    @ApiModelProperty(value = "区域干旱最大值1km")
    private BigDecimal nddiMax;

    @ApiModelProperty(value = "区域干旱标准差1km")
    private BigDecimal nddiStddev;

    @ApiModelProperty(value = "区域地温均值1km")
    private BigDecimal t;

    @ApiModelProperty(value = "区域地温最大值1km")
    private BigDecimal tMax;

    @ApiModelProperty(value = "区域地温最小值1km")
    private BigDecimal tMin;

    @ApiModelProperty(value = "区域地温标准差1km")
    private BigDecimal tStddev;

    @ApiModelProperty(value = "区域降雨均值1km")
    private BigDecimal trmm;

    @ApiModelProperty(value = "区域降雨最大值1km")
    private BigDecimal trmmMax;

    @ApiModelProperty(value = "区域降雨最小值1km")
    private BigDecimal trmmMin;

    @ApiModelProperty(value = "区域降雨标准差1km")
    private BigDecimal trmmStddev;

    @ApiModelProperty(value = "生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;

    @ApiModelProperty(value = "发布状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "发布人名称")
    private String publisherName;

    @ApiModelProperty(value = "发布人")
    private String publisher;

    private String regionName;//区域中文名

    private String regionCode;//区域Code

    private String resolutionValue;//精度值

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

    public BigDecimal getNddi() {
        return nddi;
    }

    public void setNddi(BigDecimal nddi) {
        this.nddi = nddi;
    }

    public BigDecimal getNddiMin() {
        return nddiMin;
    }

    public void setNddiMin(BigDecimal nddiMin) {
        this.nddiMin = nddiMin;
    }

    public BigDecimal getNddiMax() {
        return nddiMax;
    }

    public void setNddiMax(BigDecimal nddiMax) {
        this.nddiMax = nddiMax;
    }

    public BigDecimal getNddiStddev() {
        return nddiStddev;
    }

    public void setNddiStddev(BigDecimal nddiStddev) {
        this.nddiStddev = nddiStddev;
    }

    public BigDecimal getT() {
        return t;
    }

    public void setT(BigDecimal t) {
        this.t = t;
    }

    public BigDecimal gettMax() {
        return tMax;
    }

    public void settMax(BigDecimal tMax) {
        this.tMax = tMax;
    }

    public BigDecimal gettMin() {
        return tMin;
    }

    public void settMin(BigDecimal tMin) {
        this.tMin = tMin;
    }

    public BigDecimal gettStddev() {
        return tStddev;
    }

    public void settStddev(BigDecimal tStddev) {
        this.tStddev = tStddev;
    }

    public BigDecimal getTrmm() {
        return trmm;
    }

    public void setTrmm(BigDecimal trmm) {
        this.trmm = trmm;
    }

    public BigDecimal getTrmmMax() {
        return trmmMax;
    }

    public void setTrmmMax(BigDecimal trmmMax) {
        this.trmmMax = trmmMax;
    }

    public BigDecimal getTrmmMin() {
        return trmmMin;
    }

    public void setTrmmMin(BigDecimal trmmMin) {
        this.trmmMin = trmmMin;
    }

    public BigDecimal getTrmmStddev() {
        return trmmStddev;
    }

    public void setTrmmStddev(BigDecimal trmmStddev) {
        this.trmmStddev = trmmStddev;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
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

    public String getResolutionValue() {
        return resolutionValue;
    }

    public void setResolutionValue(String resolutionValue) {
        this.resolutionValue = resolutionValue;
    }
}
