package com.jh.collection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.collection.entity.vo.CollectionFieldModelVo;
import com.jh.entity.PageEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.util.Date;
import java.util.List;

/**
 * 采集子任务实体
 */
public class CollectionTaskItem extends PageEntity{
    private Integer id;
    private String itemName;
    private Integer taskInfoId; //采集大分类任务Id
    private String surveyAddress;//采集地址
    private String collectionStatus;//采集状态
    private String mediaId;//媒体ID逗号分隔
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date surveyTime;//调查时间
    private String position;//位置信息（坐标）
    private Date createTime;
    private Date updateTime;
    private String createor;
    private String updateor;
    private String remark;
    private Integer delFlag;
    private String phone;
    private String mediaFlag;//资源文件唯一标识，用于建立文件路径
    private Long regionId;
    private int taskType;
    private int cropId;

    private String startCreateTime;//开始创建时间
    private String endCreateTime;//结束创建时间

    public String getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    private CollectionTaskInfo collectionTaskInfo;//采集大任务信息
    private List<CollectionFieldModelVo> collectionFieldModelVoList;
    private List<CollectionMediaSource> collectionMediaSourceList;
    private List<CollectionMediaSource> amrMediaList;//录音
    private List<CollectionMediaSource> pngMediaList;//png

    public List<CollectionMediaSource> getAmrMediaList() {
        return amrMediaList;
    }

    public void setAmrMediaList(List<CollectionMediaSource> amrMediaList) {
        this.amrMediaList = amrMediaList;
    }

    public List<CollectionMediaSource> getPngMediaList() {
        return pngMediaList;
    }

    public void setPngMediaList(List<CollectionMediaSource> pngMediaList) {
        this.pngMediaList = pngMediaList;
    }

    public List<CollectionMediaSource> getCollectionMediaSourceList() {
        return collectionMediaSourceList;
    }

    public void setCollectionMediaSourceList(List<CollectionMediaSource> collectionMediaSourceList) {
        this.collectionMediaSourceList = collectionMediaSourceList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<CollectionFieldModelVo> getCollectionFieldModelVoList() {
        return collectionFieldModelVoList;
    }

    public void setCollectionFieldModelVoList(List<CollectionFieldModelVo> collectionFieldModelVoList) {
        this.collectionFieldModelVoList = collectionFieldModelVoList;
    }

    public Date getSurveyTime() {
        return surveyTime;
    }

    public void setSurveyTime(Date surveyTime) {
        this.surveyTime = surveyTime;
    }


    public CollectionTaskInfo getCollectionTaskInfo() {
        return collectionTaskInfo;
    }

    public void setCollectionTaskInfo(CollectionTaskInfo collectionTaskInfo) {
        this.collectionTaskInfo = collectionTaskInfo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskInfoId() {
        return taskInfoId;
    }

    public void setTaskInfoId(Integer taskInfoId) {
        this.taskInfoId = taskInfoId;
    }

    public String getSurveyAddress() {
        return surveyAddress;
    }

    public void setSurveyAddress(String surveyAddress) {
        this.surveyAddress = surveyAddress == null ? null : surveyAddress.trim();
    }
    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus == null ? null : collectionStatus.trim();
    }
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateor() {
        return createor;
    }

    public void setCreateor(String createor) {
        this.createor = createor == null ? null : createor.trim();
    }

    public String getUpdateor() {
        return updateor;
    }

    public void setUpdateor(String updateor) {
        this.updateor = updateor == null ? null : updateor.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMediaFlag() {
        return mediaFlag;
    }

    public void setMediaFlag(String mediaFlag) {
        this.mediaFlag = mediaFlag;
    }

}