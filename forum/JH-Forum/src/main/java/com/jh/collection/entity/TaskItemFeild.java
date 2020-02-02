package com.jh.collection.entity;

import java.util.Date;

/**
 * 采集子任务和采集字段的关联关系实体
 */
public class TaskItemFeild {
    private Integer id;
    private Integer taskItemId;//子任务ID
    private String feildNameCh;//字段中文名
    private String feildNameEn;//字段英文名
    private String collectionValue;//采集值
    private Date surveyTime;
    private Date createTime;
    private Date updateTime;
    private String createor;
    private String updateor;
    private String remark;

    private String taskName;//任务名
    private String cropName;//作物名
    private String surveyAddress;//调查地址
    private String purpose;//用途
    private String position;//位置信息

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSurveyAddress() {
        return surveyAddress;
    }

    public void setSurveyAddress(String surveyAddress) {
        this.surveyAddress = surveyAddress;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TaskItemFeild(String feildNameCh, String collectionValue) {
        this.feildNameCh = feildNameCh;
        this.collectionValue = collectionValue;
    }

    private Integer delFlag;



    public TaskItemFeild(Integer taskItemId, String feildNameCh, String feildNameEn,String collectionValue, Date createTime, Integer delFlag) {
        this.taskItemId = taskItemId;
        this.feildNameCh = feildNameCh;
        this.feildNameEn = feildNameEn;
        this.collectionValue = collectionValue;
        this.createTime = createTime;
        this.delFlag = delFlag;
    }

    public TaskItemFeild(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(Integer taskItemId) {
        this.taskItemId = taskItemId;
    }

    public String getFeildNameCh() {
        return feildNameCh;
    }

    public void setFeildNameCh(String feildNameCh) {
        this.feildNameCh = feildNameCh == null ? null : feildNameCh.trim();
    }

    public String getFeildNameEn() {
        return feildNameEn;
    }

    public void setFeildNameEn(String feildNameEn) {
        this.feildNameEn = feildNameEn == null ? null : feildNameEn.trim();
    }

    public String getCollectionValue() {
        return collectionValue;
    }

    public void setCollectionValue(String collectionValue) {
        this.collectionValue = collectionValue == null ? null : collectionValue.trim();
    }

    public Date getSurveyTime() {
        return surveyTime;
    }

    public void setSurveyTime(Date surveyTime) {
        this.surveyTime = surveyTime;
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
}