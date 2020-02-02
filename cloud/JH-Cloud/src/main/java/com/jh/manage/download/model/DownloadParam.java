package com.jh.manage.download.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.PageEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * description:
 *
 * @version <1> 2018-01-24 lcw： Created.
 */

public class DownloadParam extends PageEntity {

    private String searchStartTime;//查询开始时间
    private String searchEndTime;//查询结束时间

    private String downloadName;//任务名称
    private Integer downloadId;//下载任务主键
    private Long regionId;//区域id
    private String regionCode;//区域编码
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId",transType = CacheUtil.CACHE_TRANS_NAME)
    private String regionChinaName;//区域中文名
    private String stripNumber;//条带号
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;//影像开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;//影像结束时间
    private Integer downloadStatus;//下载任务状态
    private Integer downloadMetaId;//下载配置主键
    private String downloadMetaValue; //产品分类
    private Integer dataType; //下载数据类型
    //数据集code
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "dataType",transType = CacheUtil.CACHE_TRANS_NAME)
    private String dataTypeName;//下载数据类型名称

    private String remark;//下载任务备注
    private Integer creator;//创建人id
    private String creatorName;//创建人名称
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private Integer totalNum;//下载记录总数
    private Integer okNum;//已下载记录数
    private String downloadUrl;//下载地址
    private String path;//下载文件夹
    private Integer satId;//数据分类
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//修改时间
    private Integer modifier;//修改人
    private String modifierName;//修改人姓名
    private String dataCode;//字典编码
    private String reason;//下载任务失败原因

    private String satName;//数据分类名

    public Integer getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Integer downloadId) {
        this.downloadId = downloadId;
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

    public Integer getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Integer downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public Integer getDownloadMetaId() {
        return downloadMetaId;
    }

    public void setDownloadMetaId(Integer downloadMetaId) {
        this.downloadMetaId = downloadMetaId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getRegionChinaName() {
        return regionChinaName;
    }

    public void setRegionChinaName(String regionChinaName) {
        this.regionChinaName = regionChinaName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getSearchStartTime() {
        return searchStartTime;
    }

    public void setSearchStartTime(String searchStartTime) {
        this.searchStartTime = searchStartTime;
    }

    public String getSearchEndTime() {
        return searchEndTime;
    }

    public void setSearchEndTime(String searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public String getStripNumber() {
        return stripNumber;
    }

    public void setStripNumber(String stripNumber) {
        this.stripNumber = stripNumber;
    }

    public String getSatName() {
        return satName;
    }

    public void setSatName(String satName) {
        this.satName = satName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }


    public String getDownloadMetaValue() {
        return downloadMetaValue;
    }

    public void setDownloadMetaValue(String downloadMetaValue) {
        this.downloadMetaValue = downloadMetaValue;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }
}
