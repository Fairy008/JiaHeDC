package com.jh.briefing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.PageEntity;

import java.time.LocalDateTime;

/**
 * 模板模块
 * @version <1> 2018-04-22 lxy created
 */
public class TemplateModuleParam extends PageEntity {
    private Integer templateId;//模板主键

    private String templateName;//模板名称

    private String templateFilePosition;//模板文件位置

    private String templateMobileFilePosition;//模板手机文件位置

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//修改时间

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
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateFilePosition() {
        return templateFilePosition;
    }

    public void setTemplateFilePosition(String templateFilePosition) {
        this.templateFilePosition = templateFilePosition == null ? null : templateFilePosition.trim();
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

    public String getTemplateMobileFilePosition() {
        return templateMobileFilePosition;
    }

    public void setTemplateMobileFilePosition(String templateMobileFilePosition) {
        this.templateMobileFilePosition = templateMobileFilePosition;
    }
}