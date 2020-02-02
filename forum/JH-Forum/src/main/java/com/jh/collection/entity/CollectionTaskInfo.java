package com.jh.collection.entity;

import com.jh.entity.PageEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.util.Date;

/**
 * 采集任务（大分类）实体类
 */
public class CollectionTaskInfo extends PageEntity{
    private Integer id;
    private String phone;
    private String taskName;//任务名
    private String taskOrigin;//任务来源
    private Integer templateId;//任务模板Id
    private Integer taskType;//任务类型

    private Integer cropId;//作物
    private String purpose;//用途
    private String workplace;//工作单位
    private String templateContent;//模板内容（xml冗余）
    private Date startDate;//开始日期
    private Date endDate;//结束日期
    private Integer taskFlag;//任务标识
    private Integer indexShow;//是否首页展示
    private String indexShowImg;//首页图片展示地址
    //作物名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId",transType = CacheUtil.CACHE_TRANS_NAME)
    private String cropName;

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    /*@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")*/
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String createor;//创建者
    private String updateor;//更新者
    private String remark;//备注
    private Integer delFlag;//删除标志

    private Integer itemCount;//子任务数量

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private CollectionTemplate collectionTemplate = new CollectionTemplate();

    public CollectionTemplate getCollectionTemplate() {
        return collectionTemplate;
    }

    public void setCollectionTemplate(CollectionTemplate collectionTemplate) {
        this.collectionTemplate = collectionTemplate;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }
    public String getTaskOrigin() {
        return taskOrigin;
    }
    public void setTaskOrigin(String taskOrigin) {
        this.taskOrigin = taskOrigin == null ? null : taskOrigin.trim();
    }
    public Integer getTemplateId() {
        return templateId;
    }
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
    public Integer getTaskType() {
        return taskType;
    }
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
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
    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Integer getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(Integer taskFlag) {
        this.taskFlag = taskFlag;
    }

    public Integer getIndexShow() {
        return indexShow;
    }

    public void setIndexShow(Integer indexShow) {
        this.indexShow = indexShow;
    }

    public String getIndexShowImg() {
        return indexShowImg;
    }

    public void setIndexShowImg(String indexShowImg) {
        this.indexShowImg = indexShowImg;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}