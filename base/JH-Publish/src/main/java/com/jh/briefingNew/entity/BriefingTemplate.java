package com.jh.briefingNew.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
public class BriefingTemplate {

    private Integer templateId;//模板主键

    private String templateName;//模板名称

    private String templateFilePosition;//省市模板文件名称

    private String templateMobileFilePosition;//省市模板手机文件名称

    private String templateCounty;//区县模板文件名称

    private String templateMobileCounty;//区县手机模板文件名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//修改时间

    private Integer templateType;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateFilePosition() {
        return templateFilePosition;
    }

    public void setTemplateFilePosition(String templateFilePosition) {
        this.templateFilePosition = templateFilePosition;
    }

    public String getTemplateMobileFilePosition() {
        return templateMobileFilePosition;
    }

    public void setTemplateMobileFilePosition(String templateMobileFilePosition) {
        this.templateMobileFilePosition = templateMobileFilePosition;
    }

    public String getTemplateCounty() {
        return templateCounty;
    }

    public void setTemplateCounty(String templateCounty) {
        this.templateCounty = templateCounty;
    }

    public String getTemplateMobileCounty() {
        return templateMobileCounty;
    }

    public void setTemplateMobileCounty(String templateMobileCounty) {
        this.templateMobileCounty = templateMobileCounty;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }
}
