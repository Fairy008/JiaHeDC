package com.jh.show.agric.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * Description:
 * 1.报告信息
 *
 * @version <1> 2018-05-14 14:37 zhangshen: Created.
 */
public class DsReportEntity extends BaseEntity {
    @ApiModelProperty(value = "报告主键")
    private Integer reportId;
    @ApiModelProperty(value = "报告名称")
    private String reportName;
    @ApiModelProperty(value = "文件显示名称")
    private String fileShowname;
    @ApiModelProperty(value = "文件存放路径")
    private String filePath;
    @ApiModelProperty(value = "文件物理文件名")
    private String fileRealname;
    @ApiModelProperty(value = "文件大小")
    private String fileSize;
    @ApiModelProperty(value = "文件后缀名")
    private String fileSuffix;
    @ApiModelProperty(value = "报告周期")
    private Long cycle;
    @ApiModelProperty(value = "发布机构")
    private Long mechanism;
    @ApiModelProperty(value = "报告作物")
    private Integer cropId;
    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
    @ApiModelProperty(value = "区域主键")
    private Long regionId;
    @ApiModelProperty(value = "数据类型")
    private Integer dataType;

    @ApiModelProperty(value = "发布时间字符串")
    private String publishDateStr ;

    @ApiModelProperty(value = "数据集ID")
    private Integer dsId;

    @ApiModelProperty(value = "报告类型 ")
    private Integer approvalStatus;//自动生成9101,手动导入9102

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "报告时间")
    private LocalDateTime reportTime;

    @ApiModelProperty(value = "发布状态(2201已发布，2202待发布)")
    private Integer publishStatus;

    @ApiModelProperty(value = "发布人")
    private String publisherName;

    @ApiModelProperty(value = "分辨率")
    private Integer resolution;

    private String regionName;//区域中文名

    private String regionCode;//区域中code

    private String cropName;//农作物名称

    private String resolutionValue;//精度值

    private String dsName;//数据集中文名

    private String approvalStatusCN;//报告类型中文

    private String dataTypeCN;//精度中文

    private String publishStatusCN;//发布状态中文

    private String keyword;//关键字

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getFileShowname() {
        return fileShowname;
    }

    public void setFileShowname(String fileShowname) {
        this.fileShowname = fileShowname;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileRealname() {
        return fileRealname;
    }

    public void setFileRealname(String fileRealname) {
        this.fileRealname = fileRealname;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public Long getCycle() {
        return cycle;
    }

    public void setCycle(Long cycle) {
        this.cycle = cycle;
    }

    public Long getMechanism() {
        return mechanism;
    }

    public void setMechanism(Long mechanism) {
        this.mechanism = mechanism;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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

    public String getResolutionValue() {
        return resolutionValue;
    }

    public void setResolutionValue(String resolutionValue) {
        this.resolutionValue = resolutionValue;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getApprovalStatusCN() {
        return approvalStatusCN;
    }

    public void setApprovalStatusCN(String approvalStatusCN) {
        this.approvalStatusCN = approvalStatusCN;
    }

    public String getDataTypeCN() {
        return dataTypeCN;
    }

    public void setDataTypeCN(String dataTypeCN) {
        this.dataTypeCN = dataTypeCN;
    }

    public String getPublishStatusCN() {
        return publishStatusCN;
    }

    public void setPublishStatusCN(String publishStatusCN) {
        this.publishStatusCN = publishStatusCN;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
