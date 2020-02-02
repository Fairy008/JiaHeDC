package com.jh.collection.entity.query;

import com.jh.entity.PageEntity;

/**
 * 采集模板查询实体类
 * @version <1> 2018/12/4 14:16 xy: Created.
 */
public class CollectionTemplateQuery extends PageEntity {
    private String phone;//账户（手机号）
    private String templateName;//任务名
    private Integer cropId;//作物Id
    private Integer templateType;//模板类型
    private String startCreateTime;//结束创建时间
    private String endCreateTime;//结束创建时间
    private Integer queryFlag;//查询标志

    public Integer getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(Integer queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
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
}
