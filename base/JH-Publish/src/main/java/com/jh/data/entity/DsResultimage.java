package com.jh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class DsResultimage extends BaseEntity {


    @ApiModelProperty(value = "数据集ID")
    private Integer rgId;


    @ApiModelProperty(value = "数据集分类")
    private Integer dsType;


    @ApiModelProperty(value = "区域ID")
    private Long regionId;

    @ApiModelProperty(value = "数据日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;


    @ApiModelProperty(value = "作物ID")
    private Integer cropId;


    @ApiModelProperty(value = "分辨率")
    private Integer resolution;


    @ApiModelProperty(value = "标题")
    private String imageTitle;


    @ApiModelProperty(value = "文件路径")
    private String storagePath;

    @ApiModelProperty(value = "生产状态")
    private Integer rgStatus;

    @ApiModelProperty(value = "发布状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "发布人")
    private Integer publisher;

    @ApiModelProperty(value = "发布人名称")
    @IdTransform(type= CacheUtil.CACHE_ACCOUNT_TYPE,propName = "publisher")
    private String publisherName;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "数据集编码")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "dsType",transType = CacheUtil.CACHE_TRANS_CODE)
    private String dsCode;

    @ApiModelProperty(value = "数据集名称")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "dsType")
    private String dsName;

    @ApiModelProperty(value = "区域编码")
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String regionCode ;

    @ApiModelProperty(value = "区域名称")
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;

    @ApiModelProperty(value = "作物编码")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String cropCode;

    @ApiModelProperty(value = "作物名称")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;

    @ApiModelProperty(value = "精度编码")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution",transType = CacheUtil.CACHE_TRANS_CODE)
    private String resolutionCode;

    @ApiModelProperty(value = "精度名称")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution")
    private String resolutionName;

    @ApiModelProperty(value = "数据日期字符串")
    private String dataTimeStr;

    @ApiModelProperty(value = "时间起")
    private String startDate;

    @ApiModelProperty(value = "时间止")
    private String endDate;


    @ApiModelProperty(value = "生产状态名称")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "rgStatus")
    private String statusName;

    @ApiModelProperty(value = "发布状态名称")
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "publishStatus")
    private String publishStatusName;

    @ApiModelProperty(value="影像ID集合")
    private Integer[] rgIds;

    @ApiModelProperty(value = "图层样式")
    private String tifStyle;

    @ApiModelProperty(value = "失败原因")
    private String failMsg;

    private String dataStr;

    public Integer getRgId() {
        return rgId;
    }

    public void setRgId(Integer rgId) {
        this.rgId = rgId;
    }

    public Integer getDsType() {
        return dsType;
    }

    public void setDsType(Integer dsType) {
        this.dsType = dsType;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
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

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getDsCode() {
        return dsCode;
    }

    public void setDsCode(String dsCode) {
        this.dsCode = dsCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getDataTimeStr() {
        return dataTimeStr;
    }

    public void setDataTimeStr(String dataTimeStr) {
        this.dataTimeStr = dataTimeStr;
    }

    public Integer getRgStatus() {
        return rgStatus;
    }

    public void setRgStatus(Integer rgStatus) {
        this.rgStatus = rgStatus;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
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

    public String getResolutionName() {
        return resolutionName;
    }

    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer[] getRgIds() {
        return rgIds;
    }

    public void setRgIds(Integer[] rgIds) {
        this.rgIds = rgIds;
    }


    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPublishStatusName() {
        return publishStatusName;
    }

    public void setPublishStatusName(String publishStatusName) {
        this.publishStatusName = publishStatusName;
    }


    public String getTifStyle() {
        return tifStyle;
    }

    public void setTifStyle(String tifStyle) {
        this.tifStyle = tifStyle;
    }

    public String getResolutionCode() {
        return resolutionCode;
    }

    public void setResolutionCode(String resolutionCode) {
        this.resolutionCode = resolutionCode;
    }


    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }
}