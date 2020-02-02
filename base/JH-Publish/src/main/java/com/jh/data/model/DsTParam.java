package com.jh.data.model;

import com.jh.entity.PageEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-06-06 wl: Created.
 */
public class DsTParam  extends PageEntity {

    private Integer id;

    private Long regionId;//区域id

    private Integer resolution;//精度

    private Integer dataType;//卫星类型

    private String remark;//备注

    private String dataStatus;//数据状态

    private String delFlag;//删除标识

    private String createTime ;// 创建时间

    private String creatorName;// 创建人名称

    private  Integer creator;//创建人

    private String modifyTime ;// 修改时间

    private String modifierName; // 修改人名称

    private  Integer modifier ;//修改人

    private String publishTime;//发布时间

    private String publisherName;//发布人名称

    private Integer  publisher;//发布人

    private Integer publishStatus;//发布状态

    private String dataTime;//数据时间

    private Integer dataSet;//数据集

    private BigDecimal t;//区域干旱均值

    private BigDecimal tMin;//区域干旱最小值

    private BigDecimal tMax;//区域干旱最大值

    private BigDecimal tStddev;//区域干旱标准差

    private List<Integer> idList;

    private String startTime;//数据时间 开始时间

    private String endTime;//数据时间 结束时间

    private String regionCode;

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

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataSet() {
        return dataSet;
    }

    public void setDataSet(Integer dataSet) {
        this.dataSet = dataSet;
    }

    public BigDecimal getT() {
        return t;
    }

    public void setT(BigDecimal t) {
        this.t = t;
    }

    public BigDecimal gettMin() {
        return tMin;
    }

    public void settMin(BigDecimal tMin) {
        this.tMin = tMin;
    }

    public BigDecimal gettMax() {
        return tMax;
    }

    public void settMax(BigDecimal tMax) {
        this.tMax = tMax;
    }

    public BigDecimal gettStddev() {
        return tStddev;
    }

    public void settStddev(BigDecimal tStddev) {
        this.tStddev = tStddev;
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
}
