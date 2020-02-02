package com.jh.collection.entity.vo;

/**
 * 采集分析Vo
 */
public class CollectionAnalysisVo {

    private Integer taskItemId;//子任务ID
    private String yearStr;//年
    private Integer taskType;//任务类型和数据集对应
    private String analysisLabel;//统计分析标签
    private String analysisValue;//统计分析标签值

    public Integer getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(Integer taskItemId) {
        this.taskItemId = taskItemId;
    }

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getAnalysisLabel() {
        return analysisLabel;
    }

    public void setAnalysisLabel(String analysisLabel) {
        this.analysisLabel = analysisLabel;
    }

    public String getAnalysisValue() {
        return analysisValue;
    }

    public void setAnalysisValue(String analysisValue) {
        this.analysisValue = analysisValue;
    }
}
