package com.jh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 天气信息实体类
 */
public class DsWeather {

    private Integer id;//主键id

    @ApiModelProperty(value = "数据日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ymd;//数据时间

    private String areaName;//区域中文名

    private String bWendu;//最高温

    private String yWendu;//最低温

    private String tianQi;//天气

    private String fengXiang;//风向

    private String fengLi;//风力等级

    private String aqi;//空气质量

    private String aqiInfo;//空气质量详情

    private String aqiLevel;//空气质量等级

    private Long regionId;//区域id

    private Long parentId;//父级区域id

    private Integer isHistory;//是否历史天气

    private Integer areaId;//2345天气网的区域id

    private Integer publishStatus;//发布状态

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;//发布时间

    private String publisherName;//发布人名称

    private String delFlag;//删除标识

    private Integer publisher;//发布人

    private String publishStatusName;//发布状态

    private String regionName;//区域中文名

    private String regionCode;//区域Code

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getYmd() {
        return ymd;
    }

    public void setYmd(LocalDate ymd) {
        this.ymd = ymd;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getbWendu() {
        return bWendu;
    }

    public void setbWendu(String bWendu) {
        this.bWendu = bWendu;
    }

    public String getyWendu() {
        return yWendu;
    }

    public void setyWendu(String yWendu) {
        this.yWendu = yWendu;
    }

    public String getTianQi() {
        return tianQi;
    }

    public void setTianQi(String tianQi) {
        this.tianQi = tianQi;
    }

    public String getFengXiang() {
        return fengXiang;
    }

    public void setFengXiang(String fengXiang) {
        this.fengXiang = fengXiang;
    }

    public String getFengLi() {
        return fengLi;
    }

    public void setFengLi(String fengLi) {
        this.fengLi = fengLi;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getAqiInfo() {
        return aqiInfo;
    }

    public void setAqiInfo(String aqiInfo) {
        this.aqiInfo = aqiInfo;
    }

    public String getAqiLevel() {
        return aqiLevel;
    }

    public void setAqiLevel(String aqiLevel) {
        this.aqiLevel = aqiLevel;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getPublishStatusName() {
        return publishStatusName;
    }

    public void setPublishStatusName(String publishStatusName) {
        this.publishStatusName = publishStatusName;
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
}
