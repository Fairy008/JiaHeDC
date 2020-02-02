package com.jh.collection.entity.vo;

public class CollectionTaskItemVo {
    private Integer id;
    private Integer taskInfoId; //采集大分类任务Id
    private String surveyAddress;//采集地址
    private String mediaId;//媒体ID逗号分隔

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
        this.surveyAddress = surveyAddress;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
