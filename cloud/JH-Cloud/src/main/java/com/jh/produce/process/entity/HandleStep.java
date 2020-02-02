package com.jh.produce.process.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.produce.process.model.DataParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "数据处理步骤对象")
public class HandleStep {

    @ApiModelProperty(value = "步骤主键")
    private Integer handleDetailId;

    @ApiModelProperty(value = "数据处理任务ID")
    private Integer handleId;

    @ApiModelProperty(value = "数据处理参数配置ID")
    private Integer handleMetaId;

    @ApiModelProperty(value = "步骤序号")
    private Integer stepIndex;

    @ApiModelProperty(value = "步骤参数")
    private String stepParam;

    @ApiModelProperty(value = "步骤状态")
    private Integer handleStatus;

    @ApiModelProperty(value = "步骤开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "步骤结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "是否保存结果")
    private Boolean saveStatus;

    @ApiModelProperty(value = "失败原因")
    private String failReason;

    @ApiModelProperty(value = "步骤包含的文件对象集合")
    private List<HandleStepFile> stepFileList;

    @ApiModelProperty(value = "算法对象")
    private HandleConfig handleConfig;

    @ApiModelProperty(value = "数据路径")
    private String pendingDataPaths;

    @ApiModelProperty(value = "数据路径集合")
    private List<String> pendingDataPathList;

    @ApiModelProperty(value = "数据结果路径集合")
    private List<String> resultDataPathList;

    @ApiModelProperty(value = "数据存储集合")
    private List<DataStorage> dataStorageList;

    @ApiModelProperty(value = "数据参数集合")
    private List<DataParam> dataParamList;

    private String handleStatusCN;//步骤状态中文名称

    public Integer getHandleDetailId() {
        return handleDetailId;
    }

    public void setHandleDetailId(Integer handleDetailId) {
        this.handleDetailId = handleDetailId;
    }

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getHandleMetaId() {
        return handleMetaId;
    }

    public void setHandleMetaId(Integer handleMetaId) {
        this.handleMetaId = handleMetaId;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public String getStepParam() {
        return stepParam;
    }

    public void setStepParam(String stepParam) {
        this.stepParam = stepParam;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
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

    public Boolean getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Boolean saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public List<HandleStepFile> getStepFileList() {
        return stepFileList;
    }

    public void setStepFileList(List<HandleStepFile> stepFileList) {
        this.stepFileList = stepFileList;
    }

    public HandleConfig getHandleConfig() {
        return handleConfig;
    }

    public void setHandleConfig(HandleConfig handleConfig) {
        this.handleConfig = handleConfig;
    }

    public List<String> getPendingDataPathList() {
        return pendingDataPathList;
    }

    public void setPendingDataPathList(List<String> pendingDataPathList) {
        this.pendingDataPathList = pendingDataPathList;
    }

    public String getPendingDataPaths() {
        return pendingDataPaths;
    }

    public void setPendingDataPaths(String pendingDataPaths) {
        this.pendingDataPaths = pendingDataPaths;
    }

    public List<String> getResultDataPathList() {
        return resultDataPathList;
    }

    public void setResultDataPathList(List<String> resultDataPathList) {
        this.resultDataPathList = resultDataPathList;
    }

    public List<DataStorage> getDataStorageList() {
        return dataStorageList;
    }

    public void setDataStorageList(List<DataStorage> dataStorageList) {
        this.dataStorageList = dataStorageList;
    }

    public List<DataParam> getDataParamList() {
        return dataParamList;
    }

    public void setDataParamList(List<DataParam> dataParamList) {
        this.dataParamList = dataParamList;
    }

    public String getHandleStatusCN() {
        return handleStatusCN;
    }

    public void setHandleStatusCN(String handleStatusCN) {
        this.handleStatusCN = handleStatusCN;
    }
}