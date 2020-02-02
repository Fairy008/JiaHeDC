package com.jh.layer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * Description:
 * 1.图层数据信息
 *
 * @version <1> 2018/5/16 14:45 zhangshen: Created.
 */
@ApiModel(value = "图层数据信息")
public class DsLayer extends BaseEntity {

    @ApiModelProperty(value = "图层ID")
    private Integer layerId;

    @ApiModelProperty(value = "区域主键")
    private Long regionId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图层类型")
    private Integer type;//矢量，栅格

    @ApiModelProperty(value = "地址")
    private String url;

    @ApiModelProperty(value = "数据集")
    private Integer ds;

    @ApiModelProperty(value = "数据时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;

    @ApiModelProperty(value = "作物编号")
    private Integer cropId;

    @ApiModelProperty(value = "数据源类型")
    private Integer dataType;

    private String regionName;//区域中文名

    private String regionCode;//区域Code

    private String cropName;//农作物名称

    private String resolutionValue;//精度值

    private Integer resolution;//精度值。

    private String typeName;//文件类型 矢量

    private Integer publishStatus;//发布状态

    private LocalDateTime publishTime;//发布时间

    private String publisherName;//发布人名称

    private String publisher;//发布人编号

    private String publishStatusName;//发布状态名称

    private String dsName;//数据集名称

    private String dataTypeValue;//数据源类型

    @ApiModelProperty(value = "图层路径")
    private String filePath ;

    @ApiModelProperty(value = "再加工数据ID")
    private Integer reprocessId;

    @ApiModelProperty(value="数据集影像ID")
    private Integer resultimageId;


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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public String getPublishStatusName() {
        return publishStatusName;
    }

    public void setPublishStatusName(String publishStatusName) {
        this.publishStatusName = publishStatusName;
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

    public Integer getReprocessId() {
        return reprocessId;
    }

    public void setReprocessId(Integer reprocessId) {
        this.reprocessId = reprocessId;
    }

    public Integer getResultimageId() {
        return resultimageId;
    }

    public void setResultimageId(Integer resultimageId) {
        this.resultimageId = resultimageId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}