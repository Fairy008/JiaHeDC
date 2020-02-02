package com.jh.briefingNew.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * description:简报信息
 *
 * @version <1> 2018-07-17 wl: Created.
 */
public class BriefingReport{

    private Integer reporterId;//简报编号

    private String reporterName;//简报名称

    private String reporterContent;//简报内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    private String creator;//创建者

    private Integer audisState;//审核状态

    private Long regionId; //区域编号

    private Integer cropsId;//作物编号

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDataTimeStart;//数据开始时间

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDataTimeEnd;//数据结束时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//更新时间

    private String modifier;//更新人

    private Integer templateId;//模板编号

    private String reporterMobileContent;//简报手机版内容

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterContent() {
        return reporterContent;
    }

    public void setReporterContent(String reporterContent) {
        this.reporterContent = reporterContent;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getAudisState() {
        return audisState;
    }

    public void setAudisState(Integer audisState) {
        this.audisState = audisState;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getCropsId() {
        return cropsId;
    }

    public void setCropsId(Integer cropsId) {
        this.cropsId = cropsId;
    }

    public LocalDate getReportDataTimeStart() {
        return reportDataTimeStart;
    }

    public void setReportDataTimeStart(LocalDate reportDataTimeStart) {
        this.reportDataTimeStart = reportDataTimeStart;
    }

    public LocalDate getReportDataTimeEnd() {
        return reportDataTimeEnd;
    }

    public void setReportDataTimeEnd(LocalDate reportDataTimeEnd) {
        this.reportDataTimeEnd = reportDataTimeEnd;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getReporterMobileContent() {
        return reporterMobileContent;
    }

    public void setReporterMobileContent(String reporterMobileContent) {
        this.reporterMobileContent = reporterMobileContent;
    }
}
