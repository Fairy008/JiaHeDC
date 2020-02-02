package com.jh.cltApp.vo;

import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.entity.BaseEntity;

/**
 * 创建任务接受参数
 * @version <1> 2019/4/11 18:42 zhangshen:Created.
 */
public class CltTaskInfoParamsVO extends BaseEntity{

    private CltTaskInfo cltTaskInfo;//任务

    private CltTemplate cltTemplate;//模板

    private Integer[] userArr;//参与人的用户id数组

    private String startDateStr;

    private String endDateStr;

    public CltTaskInfo getCltTaskInfo() {
        return cltTaskInfo;
    }

    public void setCltTaskInfo(CltTaskInfo cltTaskInfo) {
        this.cltTaskInfo = cltTaskInfo;
    }

    public CltTemplate getCltTemplate() {
        return cltTemplate;
    }

    public void setCltTemplate(CltTemplate cltTemplate) {
        this.cltTemplate = cltTemplate;
    }

    public Integer[] getUserArr() {
        return userArr;
    }

    public void setUserArr(Integer[] userArr) {
        this.userArr = userArr;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
}
