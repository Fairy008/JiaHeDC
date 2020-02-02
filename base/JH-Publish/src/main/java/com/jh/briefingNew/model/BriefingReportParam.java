package com.jh.briefingNew.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.PageEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * description:生成简报的参数
 *
 * @version <1> 2018-07-18 wl: Created.
 */
public class BriefingReportParam extends PageEntity {

    private Long reporterId;//简报编号

    private Integer [] reporterIds;//简报编号集合，用于批量简报批量发布的状态设置

    private String reporterName;//简报名称

    private String reporterContent;//简报内容

    private String reporterMobileContent;//简报手机版内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    private String creator;//创建人

    private Integer audisState;//审核状态

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDataTimeStart;//数据开始时间

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDataTimeEnd;//数据结束时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//更新时间

    private String modifier;//更新人
    private String creatorNickName;//创建人昵称
    private String modifierNickName;//更新人昵称

    private Integer templateId;//模板编号
    private String lastGrowthDate;//物候期最后时间

    /**
     *额外参数
     */
    private Long regionId; //区域编号
    private String regionName;//区域名称
    private Integer level;//区域级别
    private Long regionParentId;//父区域编号
    private Integer cropsId;//作物编号
    private String cropsName;//作物名称
    private String startDate; //查询开始时间
    private String endDate;//查询结束时间
    private String growthStartDate;//播种期
    private String growthName;//物候期名称
    private Integer growthId;//物候期编号
    private String startDateNoYear;//开始时间(不带年份)
    private String endDateNoYear;//结束时间(不带年份)
    private Integer lastYear;//去年年份
    private String startDateMonth;//月开始时间（用于模板中历史同期月开始时间）
    private String endDateMonth;//月结束时间（用于模板中历史同期月结束时间）
    private String startDateMonthNoYear;//月开始时间,不带年（用于模板中历史同期月开始时间）
    private String endDateMonthNoYear;//月结束时间,不带年（用于模板中历史同期月结束时间）
    private LocalDate startLocalDate;//开始时间 LocalDate类型
    private LocalDate endLocalDate;//结束时间 LocalDate类型
    private String remark;//备注信息
    private Integer spanYearForWeek;//本期开始时间和结束时间段，跨年间隔数
    private Integer spanYearForMonth;//按月度计算,跨年间隔数

    private Integer history;//是否查询历史数据  1表示查询历史数据 0表示查询当前数据

    private String pcBriefReportUrl;//pc端简报地址

    private String mobileBriefReportUrl;//mobile端简报地址

    private Integer ifWinter;//是否为冬季作物  1 是  0 否   如果非冬季作物 有效积温只计算10度以上的



    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public Integer[] getReporterIds() {
        return reporterIds;
    }

    public void setReporterIds(Integer[] reporterIds) {
        this.reporterIds = reporterIds;
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

    public String getCreatorNickName() {
        return creatorNickName;
    }

    public void setCreatorNickName(String creatorNickName) {
        this.creatorNickName = creatorNickName;
    }

    public String getModifierNickName() {
        return modifierNickName;
    }

    public void setModifierNickName(String modifierNickName) {
        this.modifierNickName = modifierNickName;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getLastGrowthDate() {
        return lastGrowthDate;
    }

    public void setLastGrowthDate(String lastGrowthDate) {
        this.lastGrowthDate = lastGrowthDate;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getRegionParentId() {
        return regionParentId;
    }

    public void setRegionParentId(Long regionParentId) {
        this.regionParentId = regionParentId;
    }

    public Integer getCropsId() {
        return cropsId;
    }

    public void setCropsId(Integer cropsId) {
        this.cropsId = cropsId;
    }

    public String getCropsName() {
        return cropsName;
    }

    public void setCropsName(String cropsName) {
        this.cropsName = cropsName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGrowthStartDate() {
        return growthStartDate;
    }

    public void setGrowthStartDate(String growthStartDate) {
        this.growthStartDate = growthStartDate;
    }

    public String getGrowthName() {
        return growthName;
    }

    public void setGrowthName(String growthName) {
        this.growthName = growthName;
    }

    public Integer getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Integer growthId) {
        this.growthId = growthId;
    }

    public String getStartDateNoYear() {
        return startDateNoYear;
    }

    public void setStartDateNoYear(String startDateNoYear) {
        this.startDateNoYear = startDateNoYear;
    }

    public String getEndDateNoYear() {
        return endDateNoYear;
    }

    public void setEndDateNoYear(String endDateNoYear) {
        this.endDateNoYear = endDateNoYear;
    }

    public Integer getLastYear() {
        return lastYear;
    }

    public void setLastYear(Integer lastYear) {
        this.lastYear = lastYear;
    }

    public String getStartDateMonth() {
        return startDateMonth;
    }

    public void setStartDateMonth(String startDateMonth) {
        this.startDateMonth = startDateMonth;
    }

    public String getEndDateMonth() {
        return endDateMonth;
    }

    public void setEndDateMonth(String endDateMonth) {
        this.endDateMonth = endDateMonth;
    }

    public String getStartDateMonthNoYear() {
        return startDateMonthNoYear;
    }

    public void setStartDateMonthNoYear(String startDateMonthNoYear) {
        this.startDateMonthNoYear = startDateMonthNoYear;
    }

    public String getEndDateMonthNoYear() {
        return endDateMonthNoYear;
    }

    public void setEndDateMonthNoYear(String endDateMonthNoYear) {
        this.endDateMonthNoYear = endDateMonthNoYear;
    }

    public LocalDate getStartLocalDate() {
        return startLocalDate;
    }

    public void setStartLocalDate(LocalDate startLocalDate) {
        this.startLocalDate = startLocalDate;
    }

    public LocalDate getEndLocalDate() {
        return endLocalDate;
    }

    public void setEndLocalDate(LocalDate endLocalDate) {
        this.endLocalDate = endLocalDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSpanYearForWeek() {
        return spanYearForWeek;
    }

    public void setSpanYearForWeek(Integer spanYearForWeek) {
        this.spanYearForWeek = spanYearForWeek;
    }

    public Integer getSpanYearForMonth() {
        return spanYearForMonth;
    }

    public void setSpanYearForMonth(Integer spanYearForMonth) {
        this.spanYearForMonth = spanYearForMonth;
    }

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }

    public String getPcBriefReportUrl() {
        return pcBriefReportUrl;
    }

    public void setPcBriefReportUrl(String pcBriefReportUrl) {
        this.pcBriefReportUrl = pcBriefReportUrl;
    }

    public String getMobileBriefReportUrl() {
        return mobileBriefReportUrl;
    }

    public void setMobileBriefReportUrl(String mobileBriefReportUrl) {
        this.mobileBriefReportUrl = mobileBriefReportUrl;
    }

    public Integer getIfWinter() {
        return ifWinter;
    }

    public void setIfWinter(Integer ifWinter) {
        this.ifWinter = ifWinter;
    }
}
