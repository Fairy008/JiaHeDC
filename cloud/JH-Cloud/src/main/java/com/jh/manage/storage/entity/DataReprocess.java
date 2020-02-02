package com.jh.manage.storage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * Description:
 * 1.再加工数据入库对象实体
 *
 * @version <1> 2018-04-25 9:18 zhangshen: Created.
 */
@ApiModel(value = "再加工数据入库对象")
public class DataReprocess extends BaseEntity {

    @ApiModelProperty(value = "再加工数据入库主键ID")
    private Integer reprocessId;


    @ApiModelProperty(value = "区域ID")
    private Long regionId;

    @ApiModelProperty(value = "卫星")
    private String satellite;

    @ApiModelProperty(value = "传感器")
    private String sensor;

    @ApiModelProperty(value = "关键字")
    private String words;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "数据日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;

    @ApiModelProperty(value = "数据日期参数")
    private String productTime;

    @ApiModelProperty(value = "区域名称")
    private String regionName;

    private String productTimeBegin;//数据日期 开始时间

    private String productTimeEnd;//数据日期 结束时间

    private Integer publishStatus;//发布状态

    @ApiModelProperty(value = "数据集")
    private Integer dataSet;//数据集

    private String dataSetCN;//数据集中文名

    private String publishStatusCN;//发布状态中文名

    public Integer getReprocessId() {
        return reprocessId;
    }

    public void setReprocessId(Integer reprocessId) {
        this.reprocessId = reprocessId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getProductTimeBegin() {
        return productTimeBegin;
    }

    public void setProductTimeBegin(String productTimeBegin) {
        this.productTimeBegin = productTimeBegin;
    }

    public String getProductTimeEnd() {
        return productTimeEnd;
    }

    public void setProductTimeEnd(String productTimeEnd) {
        this.productTimeEnd = productTimeEnd;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Integer getDataSet() {
        return dataSet;
    }

    public void setDataSet(Integer dataSet) {
        this.dataSet = dataSet;
    }

    public String getDataSetCN() {
        return dataSetCN;
    }

    public void setDataSetCN(String dataSetCN) {
        this.dataSetCN = dataSetCN;
    }

    public String getPublishStatusCN() {
        return publishStatusCN;
    }

    public void setPublishStatusCN(String publishStatusCN) {
        this.publishStatusCN = publishStatusCN;
    }
}
