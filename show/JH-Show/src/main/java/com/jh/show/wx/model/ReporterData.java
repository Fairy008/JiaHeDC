package com.jh.show.wx.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 简报数据
 * @version <1> 2018-05-06 lxy： Created.
 */
public class ReporterData {
    private Long reporterId;//简报编号

    private String reporterName;//简报名称

    private String reporterContent;//简报内容

    private String reporterMobileContent;//简报手机版内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
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

    public String getReporterMobileContent() {
        return reporterMobileContent;
    }

    public void setReporterMobileContent(String reporterMobileContent) {
        this.reporterMobileContent = reporterMobileContent;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
