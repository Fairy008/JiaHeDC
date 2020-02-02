package com.jh.collection.entity.query;

import com.jh.entity.PageEntity;

/**
 * 采集任务（大分类）查询实体类
 * @version <1> 2018/12/4 14:16 xy: Created.
 */
public class CollectionTaskInfoQuery extends PageEntity {
    private String taskName;//任务名
    private Integer cropId;//作物Id
    private Integer taskType;//任务类型
    private String phone;//手机号

    private String startCreateTime;//结束创建时间
    private String endCreateTime;//结束创建时间
    private Integer indexShow;//是否首页展示

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

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

    public Integer getIndexShow() {
        return indexShow;
    }

    public void setIndexShow(Integer indexShow) {
        this.indexShow = indexShow;
    }
}
