package com.jh.layer.model;


import com.jh.entity.PageEntity;

import java.util.Date;
import java.util.List;

/**
 * description:图层
 *
 * @version <1> 2018-05-17 wl: Created.
 */
public class DsLayerParam extends PageEntity {

    private Integer layerId;

    private Long regionId;

    private String title;

    private String name;

    private Integer type;//图层类型 矢量、栅格

    private String url;

    private Integer ds;//数据集

    private Integer cropId;//作物

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

    private Integer resolution;//精度

    private Date dataTime;//数据时间

    private Integer dataType;//数据源类型

    private List<Integer> idList;//批量发布 撤销 id数组

    private Integer dataSet;//数据集

    private String startTime;//数据时间 开始时间

    private String endTime;//数据时间 结束时间

    private String remark;//备注

    private String publishStatusName;//发布状态名称

    private String dataStatus;// 数据状态（1有效，0无效)

    private String delFlag; //删除标记

    private String typeName;//图层类别

    private String regionName;//区域中文名

    private String regionCode;//区域编号

    private String dsName;//数据集名称

    private String dataTypeValue;//数据源类型

    List<Integer> dsList;//数据集编号集合

    String dsListStr;//数据集编号集合--字符串格式数组，

    private Integer layerStyleId;//图层样式

    private String layerStyleName;//样式名

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

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerId) {
        this.layerId = layerId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDs() {
        return ds;
    }

    public void setDs(Integer ds) {
        this.ds = ds;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
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

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
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

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataSet() {
        return dataSet;
    }

    public void setDataSet(Integer dataSet) {
        this.dataSet = dataSet;
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


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getPublishStatusName() {
        return publishStatusName;
    }

    public void setPublishStatusName(String publishStatusName) {
        this.publishStatusName = publishStatusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDataTypeValue() {
        return dataTypeValue;
    }

    public void setDataTypeValue(String dataTypeValue) {
        this.dataTypeValue = dataTypeValue;
    }

    public List<Integer> getDsList() {
        return dsList;
    }

    public void setDsList(List<Integer> dsList) {
        this.dsList = dsList;
    }

    public String getDsListStr() {
        return dsListStr;
    }

    public void setDsListStr(String dsListStr) {
        this.dsListStr = dsListStr;
    }

    public Integer getLayerStyleId() {
        return layerStyleId;
    }

    public void setLayerStyleId(Integer layerStyleId) {
        this.layerStyleId = layerStyleId;
    }

    public String getLayerStyleName() {
        return layerStyleName;
    }

    public void setLayerStyleName(String layerStyleName) {
        this.layerStyleName = layerStyleName;
    }
}
