package com.jh.manage.loader.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "数据入库对象")
public class DataLoader extends BaseEntity {

    @ApiModelProperty(value = "入库主键")
    private Integer loaderId;

    @ApiModelProperty(value = "任务名称")
    private String loaderName;

    @ApiModelProperty(value = "入库总量")
    private Integer totalNum;

    @ApiModelProperty(value = "入库完成量")
    private Integer okNum;

    @ApiModelProperty(value = "入库类型")
    private Integer loaderType;

    @ApiModelProperty(value = "源数据路径")
    private String dataStoragePath;

    @ApiModelProperty(value = "数据类型")
    private Integer storageType;

    @ApiModelProperty(value = "入库状态")
    private Integer loaderStatus;


    @ApiModelProperty(value = "步骤开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "步骤结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "失败原因")
    private String reason;


//
//    @ApiModelProperty(value = "区域ID")
//    private Long regionId;
//
//    @ApiModelProperty(value = "卫星")
//    private String satellite;
//
//    @ApiModelProperty(value = "传感器")
//    private String sensor;
//
//    @ApiModelProperty(value = "关键字")
//    private String words;
//
//    @ApiModelProperty(value = "数据日期")
//    private Date dataTime;

    @ApiModelProperty(value = "入库明细对象集合")
    private List<DataLoaderDetail> loaderDetailList;

    public Integer getLoaderId() {
        return loaderId;
    }

    public void setLoaderId(Integer loaderId) {
        this.loaderId = loaderId;
    }

    public String getLoaderName() {
        return loaderName;
    }

    public void setLoaderName(String loaderName) {
        this.loaderName = loaderName;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getOkNum() {
        return okNum;
    }

    public void setOkNum(Integer okNum) {
        this.okNum = okNum;
    }

    public Integer getLoaderType() {
        return loaderType;
    }

    public void setLoaderType(Integer loaderType) {
        this.loaderType = loaderType;
    }

    public String getDataStoragePath() {
        return dataStoragePath;
    }

    public void setDataStoragePath(String dataStoragePath) {
        this.dataStoragePath = dataStoragePath;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public Integer getLoaderStatus() {
        return loaderStatus;
    }

    public void setLoaderStatus(Integer loaderStatus) {
        this.loaderStatus = loaderStatus;
    }

    public List<DataLoaderDetail> getLoaderDetailList() {
        return loaderDetailList;
    }

    public void setLoaderDetailList(List<DataLoaderDetail> loaderDetailList) {
        this.loaderDetailList = loaderDetailList;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

//    public Long getRegionId() {
//        return regionId;
//    }
//
//    public void setRegionId(Long regionId) {
//        this.regionId = regionId;
//    }
//
//    public String getSatellite() {
//        return satellite;
//    }
//
//    public void setSatellite(String satellite) {
//        this.satellite = satellite;
//    }
//
//    public String getSensor() {
//        return sensor;
//    }
//
//    public void setSensor(String sensor) {
//        this.sensor = sensor;
//    }
//
//    public String getWords() {
//        return words;
//    }
//
//    public void setWords(String words) {
//        this.words = words;
//    }
//
//    public Date getDataTime() {
//        return dataTime;
//    }
//
//    public void setDataTime(Date dataTime) {
//        this.dataTime = dataTime;
//    }
}