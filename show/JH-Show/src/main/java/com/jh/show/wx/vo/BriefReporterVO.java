package com.jh.show.wx.vo;

/**
 * 简报视图
 * @version <1> 2018-05-09 lxy： Created.
 */
public class BriefReporterVO {
    private Long reporterId;//简报编号
    private Long regionId;//区域编号
    private String wxId;//编号
    Integer cropsId;//作物编号
    String reportDataTimeEnd;//简报最新事件
    String reporterMobileContent;//简报内容
    private String regionName;//区域名称
    private String cropName;//作物名称
    private String reporterName;//简报标题

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public Integer getCropsId() {
        return cropsId;
    }

    public void setCropsId(Integer cropsId) {
        this.cropsId = cropsId;
    }

    public String getReportDataTimeEnd() {
        return reportDataTimeEnd;
    }

    public void setReportDataTimeEnd(String reportDataTimeEnd) {
        this.reportDataTimeEnd = reportDataTimeEnd;
    }

    public String getReporterMobileContent() {
        return reporterMobileContent;
    }

    public void setReporterMobileContent(String reporterMobileContent) {
        this.reporterMobileContent = reporterMobileContent;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}
