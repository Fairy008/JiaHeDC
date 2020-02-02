package com.jh.manage.storage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value="元数据对象")
public class DataStorage {

    @ApiModelProperty(value = "元数据主键")
    private Integer storageId;

    @ApiModelProperty(value = "卫星ID")
    private String satellite;

    @ApiModelProperty(value = "传感器或影像类型ID")
    private String sensor;

    @ApiModelProperty(value = "产品级别ID")
    private String productLevel;

    @ApiModelProperty(value = "景序列号")
    private String sceneId;

    @ApiModelProperty(value = "文件名")
    private String fileName ;

    @ApiModelProperty(value = "数据版本")
    private String storageVersion;

    @ApiModelProperty(value = "数据类型")
    private Integer storageType;

    @ApiModelProperty(value = "存储地址")
    private String storageUrl;

    @ApiModelProperty(value = "数据大小")
    private String dataSize;

    @ApiModelProperty(value = "缩略图URL")
    private String thumbnailUrl;

    @ApiModelProperty(value = "空间分辨率")
    private String resolution;

    @ApiModelProperty(value = "影像宽")
    private Long dataWidth;

    @ApiModelProperty(value = "影像高")
    private Long dataHeight;

    @ApiModelProperty(value = "区域范围")
    private String bbox;

    @ApiModelProperty(value = "云量")
    private BigDecimal cloudPercent;

    @ApiModelProperty(value = "产品时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;

    @ApiModelProperty(value = "元数据XML")
    private String dataXml;

    @ApiModelProperty(value = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;


    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStorageVersion() {
        return storageVersion;
    }

    public void setStorageVersion(String storageVersion) {
        this.storageVersion = storageVersion == null ? null : storageVersion.trim();
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl == null ? null : storageUrl.trim();
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize == null ? null : dataSize.trim();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl == null ? null : thumbnailUrl.trim();
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution == null ? null : resolution.trim();
    }

    public Long getDataWidth() {
        return dataWidth;
    }

    public void setDataWidth(Long dataWidth) {
        this.dataWidth = dataWidth;
    }

    public Long getDataHeight() {
        return dataHeight;
    }

    public void setDataHeight(Long dataHeight) {
        this.dataHeight = dataHeight;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }

    public BigDecimal getCloudPercent() {
        return cloudPercent;
    }

    public void setCloudPercent(BigDecimal cloudPercent) {
        this.cloudPercent = cloudPercent;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataXml() {
        return dataXml;
    }

    public void setDataXml(String dataXml) {
        this.dataXml = dataXml == null ? null : dataXml.trim();
    }


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}