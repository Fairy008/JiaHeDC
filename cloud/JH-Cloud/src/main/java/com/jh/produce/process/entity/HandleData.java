package com.jh.produce.process.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.manage.storage.entity.DataStorage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "数据处理任务")
public class HandleData extends BaseEntity {

    @ApiModelProperty(value = "数据处理任务主键")
    private Integer handleId;

    @ApiModelProperty(value = "数据处理任务名称")
    private String handleName;

    @ApiModelProperty(value = "区域ID")
    private Long regionId;

    @ApiModelProperty(value = "区域名称")
    private String chinaName;

    @ApiModelProperty(value = "区域编码")
    private String regionCode;

    @ApiModelProperty(value = "卫星ID")
    private Integer satId;

    @ApiModelProperty(value = "卫星名称")
    private String satName;

    @ApiModelProperty(value = "数据类型")
    private Integer storageType;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "失败原因")
    private String failReason;

    @ApiModelProperty(value = "执行状态")
    private Integer handleStatus;

    @ApiModelProperty(value = "影像开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "影像结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    @ApiModelProperty(value = "任务类型")
    private Integer taskType;

//    private Integer[] storageIdArray;


    @ApiModelProperty(value = "数据处理任务的源数据集合")
    private List<RelateTaskStorage> relateTaskStorageList;

    @ApiModelProperty(value = "数据处理步骤对象集合")
    private List<HandleStep> handleStepList;

    @ApiModelProperty(value = "数据文件对象集合")
    private List<DataStorage> dataStorageList;

//    private Map<Integer,String> relateStorageLink;//原始文件对应的link 地址

    private String satNameCN;//数据分类中文名称

    private String handleStatusCN;//任务状态中文名称

    private String storageUrl;//文件路径

    private String storageTypeCN;//数据类型中文名称

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
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

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
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

//    public Integer[] getStorageIdArray() {
//        return storageIdArray;
//    }
//
//    public void setStorageIdArray(Integer[] storageIdArray) {
//        this.storageIdArray = storageIdArray;
//    }

    public List<HandleStep> getHandleStepList() {
        return handleStepList;
    }

    public void setHandleStepList(List<HandleStep> handleStepList) {
        this.handleStepList = handleStepList;
    }

    public String getSatName() {
        return satName;
    }

    public void setSatName(String satName) {
        this.satName = satName;
    }

    public List<DataStorage> getDataStorageList() {
        return dataStorageList;
    }

    public void setDataStorageList(List<DataStorage> dataStorageList) {
        this.dataStorageList = dataStorageList;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

//    public Map<Integer, String> getRelateStorageLink() {
//        return relateStorageLink;
//    }
//
//    public void setRelateStorageLink(Map<Integer, String> relateStorageLink) {
//        this.relateStorageLink = relateStorageLink;
//    }

    public List<RelateTaskStorage> getRelateTaskStorageList() {
        return relateTaskStorageList;
    }

    public void setRelateTaskStorageList(List<RelateTaskStorage> relateTaskStorageList) {
        this.relateTaskStorageList = relateTaskStorageList;
    }

    public String getSatNameCN() {
        return satNameCN;
    }

    public void setSatNameCN(String satNameCN) {
        this.satNameCN = satNameCN;
    }

    public String getHandleStatusCN() {
        return handleStatusCN;
    }

    public void setHandleStatusCN(String handleStatusCN) {
        this.handleStatusCN = handleStatusCN;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public String getStorageTypeCN() {
        return storageTypeCN;
    }

    public void setStorageTypeCN(String storageTypeCN) {
        this.storageTypeCN = storageTypeCN;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }
}