package com.jh.data.model;


import com.jh.entity.PageEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-05-08 wl: Created.
 */
public class DsTtnParam extends PageEntity{
    private Integer id;

    private Long regionId;

    private Integer  cropId;

    private Integer resolution;

    private Integer dataType;

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

    private BigDecimal nddi;//区域干旱均值

    private BigDecimal nddiMin;//区域干旱最小值

    private BigDecimal nddiMax;//区域干旱最大值

    private BigDecimal nddiStddev;//区域干旱标准差

    private BigDecimal t;//区域干旱均值

    private BigDecimal tMin;//区域干旱最小值

    private BigDecimal tMax;//区域干旱最大值

    private BigDecimal tStddev;//区域干旱标准差

    private BigDecimal trmm;//区域干旱均值

    private BigDecimal trmmMin;//区域干旱最小值

    private BigDecimal trmmMax;//区域干旱最大值

    private BigDecimal trmmStddev;//区域干旱标准差

    private List<Integer> idList;

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

    public BigDecimal getTrmm() {
        return trmm;
    }

    public void setTrmm(BigDecimal trmm) {
        this.trmm = trmm;
    }

    public BigDecimal getTrmmMin() {
        return trmmMin;
    }

    public void setTrmmMin(BigDecimal trmmMin) {
        this.trmmMin = trmmMin;
    }

    public BigDecimal getTrmmMax() {
        return trmmMax;
    }

    public void setTrmmMax(BigDecimal trmmMax) {
        this.trmmMax = trmmMax;
    }

    public BigDecimal getTrmmStddev() {
        return trmmStddev;
    }

    public void setTrmmStddev(BigDecimal trmmStddev) {
        this.trmmStddev = trmmStddev;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}
