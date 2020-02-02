package com.jh.manage.download.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.List;

@ApiModel(value = "数据下载任务对象")
public class DataDownload extends BaseEntity {
    @ApiModelProperty(value = "数据下载任务主键")
    private Integer downloadId;

    @ApiModelProperty(value = "任务名称")
    private String downloadName;


    @ApiModelProperty(value = "数据分类ID")
    private Integer downloadMetaId;

    @ApiModelProperty(value="数据分类值")
    private String downloadMetaValue;

    @ApiModelProperty(value = "区域ID")
    private Long regionId;


    @ApiModelProperty(value = "数据日期起")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;


    @ApiModelProperty(value = "数据日期止")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "数据文件总数")
    private Integer totalNum;


    @ApiModelProperty(value = "完成下载数")
    private Integer okNum;


    @ApiModelProperty(value = "数据下载状态")
    private Integer downloadStatus;


    //创建下载任务时，根据任务信息确定数据存储路径
    @ApiModelProperty(value = "数据存储位置")
    private String storagePath;

    @ApiModelProperty(value = "失败原因")
    private String reason;

    @ApiModelProperty(value = "下载数据类型")
    private Integer dataType;

    @ApiModelProperty(value = "数据下载明细对象集合")
    private List<DataDownloadDetail> downloadDetailList;

    public Integer getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Integer downloadId) {
        this.downloadId = downloadId;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public Integer getDownloadMetaId() {
        return downloadMetaId;
    }

    public void setDownloadMetaId(Integer downloadMetaId) {
        this.downloadMetaId = downloadMetaId;
    }

    public String getDownloadMetaValue() {
        return downloadMetaValue;
    }

    public void setDownloadMetaValue(String downloadMetaValue) {
        this.downloadMetaValue = downloadMetaValue;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public Integer getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Integer downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public List<DataDownloadDetail> getDownloadDetailList() {
        return downloadDetailList;
    }

    public void setDownloadDetailList(List<DataDownloadDetail> downloadDetailList) {
        this.downloadDetailList = downloadDetailList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}