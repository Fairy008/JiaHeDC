package com.jh.collection.entity;

import com.jh.collection.entity.vo.CollectionFieldModelVo;

import java.util.Date;
import java.util.List;

/**
 * 用户模板实体类
 * @version <1> 2018-12-04 xy： Created.
 */
public class CollectionTemplate {

    private Integer id;
    private String templateName;//模板名
    private String phone;
    private Integer cropId;//作物Id
    private Integer templateType;//模板类型
    private String purpose;//用途
    private String workplace;//工作单位
    private String templateContent;//模板内容

    private String encrypt;//任务模板唯一标识
    private String spaceType;//空间类型
    private Date createTime;
    private Date updateTime;
    private String createor;
    private String updateor;
    private String remark;
    private Integer delFlag;
    private List<CollectionFieldModelVo> collectionFieldModelVoList;//需采集的任务字段
    private String spaceTypeMsg;//空间类型信息
    private Integer templateFlag;//模板标识（1：公用模板，2：非公用模板）

    public Integer getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Integer templateFlag) {
        this.templateFlag = templateFlag;
    }

    public String getSpaceTypeMsg() {
        return spaceTypeMsg;
    }

    public void setSpaceTypeMsg(String spaceTypeMsg) {
        this.spaceTypeMsg = spaceTypeMsg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CollectionFieldModelVo> getCollectionFieldModelVoList() {
        return collectionFieldModelVoList;
    }

    public void setCollectionFieldModelVoList(List<CollectionFieldModelVo> collectionFieldModelVoList) {
        this.collectionFieldModelVoList = collectionFieldModelVoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace == null ? null : workplace.trim();
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt == null ? null : encrypt.trim();
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType == null ? null : spaceType.trim();
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

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    @Override
    public String toString() {
        return "CollectionTemplate{" +
                "phone=" + phone +
                ", templateName='" + templateName + '\'' +
                ", cropId=" + cropId +
                ", templateType=" + templateType +
                ", purpose='" + purpose + '\'' +
                ", workplace='" + workplace + '\'' +
                ", spaceType='" + spaceType + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}