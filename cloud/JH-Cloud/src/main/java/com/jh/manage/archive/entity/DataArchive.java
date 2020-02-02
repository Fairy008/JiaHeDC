package com.jh.manage.archive.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "数据归档对象")
public class DataArchive extends BaseEntity {

    @ApiModelProperty(value = "归档任务主键")
    private Integer archiveId;

    @ApiModelProperty(value = "归档任务名称")
    private String archiveName;

    @ApiModelProperty(value = "归档数据时间起")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @ApiModelProperty(value = "归档数据时间止")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    @ApiModelProperty(value = "卫星ID")
    private Integer satId;

    @ApiModelProperty(value = "归档任务执行时间起")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "归档任务执行时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "归档数据文件总数")
    private Integer totalNum;

    @ApiModelProperty(value = "归档路径")
    private String archivePath;

    @ApiModelProperty(value = "归档脚本命令")
    private String archiveCommand;

    @ApiModelProperty(value = "归档状态")
    private Integer archiveStatus;


    @ApiModelProperty(value = "失败原因")
    private String reason;

    @ApiModelProperty(value = "卫星名称")
    private String satName;
    /**
     * 归档明细对象集合
     */
    @ApiModelProperty(value = "归档明细对象集合")
    private List<DataArchiveDetail> archiveDetailList;

    @ApiModelProperty(value = "今日卫星归档数据")
    private Integer today;

    @ApiModelProperty(value = "历史总计卫星归档数据")
    private Integer total;

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName == null ? null : archiveName.trim();
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getArchivePath() {
        return archivePath;
    }

    public void setArchivePath(String archivePath) {
        this.archivePath = archivePath == null ? null : archivePath.trim();
    }

    public String getArchiveCommand() {
        return archiveCommand;
    }

    public void setArchiveCommand(String archiveCommand) {
        this.archiveCommand = archiveCommand == null ? null : archiveCommand.trim();
    }

    public Integer getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(Integer archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public List<DataArchiveDetail> getArchiveDetailList() {
        return archiveDetailList;
    }

    public void setArchiveDetailList(List<DataArchiveDetail> archiveDetailList) {
        this.archiveDetailList = archiveDetailList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSatName() {
        return satName;
    }

    public void setSatName(String satName) {
        this.satName = satName;
    }

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}