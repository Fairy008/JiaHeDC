package com.jh.data.model;


import com.jh.entity.PageEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-09-29 huxiaoqiang: Created.
 */
public class DsWeatherParam extends PageEntity{

    private Integer id;//主键id

    private Long regionId;//区域id

    private String tianQi;//天气状况

    private String bWendu;//最高温度

    private String yWendu;//最低温度

    private String fengXiang;//风向

    private String fengLi;//风力

    private String aqi;//空气质量

    private String aqiInfo;//空气质量详情

    private String aqiLevel;//空气质量等级

    private String delFlag;//删除标识

    private String publishTime;//发布时间

    private String publisherName;//发布人名称

    private Integer  publisher;//发布人

    private Integer publishStatus;//发布状态

    private List<Integer> idList;

    private String startTime;//数据时间 开始时间

    private String endTime;//数据时间 结束时间

    private String regionCode;

    private String modifierName; // 修改人名称

    private  Integer modifier ;//修改人


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

    public String getTianQi() {
        return tianQi;
    }

    public void setTianQi(String tianQi) {
        this.tianQi = tianQi;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }
}
