package com.jh.report.model;


import com.jh.entity.PageEntity;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-05-15 14:54 zhangshen: Created.
 */
public class DsReportParam extends PageEntity {

    private Long regionId;//区域id

    private Integer cropId;//作物

    private Integer dsId;//数据集

    private Integer resolution;//分辨率

    private Integer approvalStatus;//报告类型

    private String reportTime;//报告时间

    private String startTimeReport;//报告时间 开始时间

    private String endTimeReport;//报告时间 结束时间

    private String creatorName;// 创建人名称

    private String startTimeCreate;//创建时间 开始时间

    private String endTimeCreate;//创建时间 结束时间

    private Integer dataType;//卫星

    private Integer publishStatus;//发布状态

    private String keyword;//关键字

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

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getStartTimeReport() {
        return startTimeReport;
    }

    public void setStartTimeReport(String startTimeReport) {
        this.startTimeReport = startTimeReport;
    }

    public String getEndTimeReport() {
        return endTimeReport;
    }

    public void setEndTimeReport(String endTimeReport) {
        this.endTimeReport = endTimeReport;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getStartTimeCreate() {
        return startTimeCreate;
    }

    public void setStartTimeCreate(String startTimeCreate) {
        this.startTimeCreate = startTimeCreate;
    }

    public String getEndTimeCreate() {
        return endTimeCreate;
    }

    public void setEndTimeCreate(String endTimeCreate) {
        this.endTimeCreate = endTimeCreate;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
