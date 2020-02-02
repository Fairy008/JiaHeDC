package com.jh.collection.entity.vo;

/**
 * 采集任务字段值
 * @version <1> 2018/12/5 10:11 xy: Created.
 */
public class CollectionTaskFeildItem {

    private Integer taskItemId;//子任务ID
    private String feildNameCh;//字段中文名
    private String feildNameEn;//字段英文名
    private String collectionValue;//采集值

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
        this.feildNameCh = feildNameCh;
    }

    public String getFeildNameEn() {
        return feildNameEn;
    }

    public void setFeildNameEn(String feildNameEn) {
        this.feildNameEn = feildNameEn;
    }

    public String getCollectionValue() {
        return collectionValue;
    }

    public void setCollectionValue(String collectionValue) {
        this.collectionValue = collectionValue;
    }
}
