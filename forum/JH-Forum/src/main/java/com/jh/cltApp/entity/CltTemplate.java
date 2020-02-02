package com.jh.cltApp.entity;

import com.jh.cltApp.utils.BeanToXmlUtils;
import com.jh.cltApp.vo.TemplateVO;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;
import org.apache.commons.lang3.StringUtils;

public class CltTemplate extends BaseEntity {

    private TemplateVO templateVO;

    public TemplateVO getTemplateVO() {
        if (StringUtils.isNotEmpty(this.getContent())) {
            templateVO = BeanToXmlUtils.xml2Bean(TemplateVO.class, this.getContent());
        }
        return templateVO;
    }

    public void setTemplateVO(TemplateVO templateVO) {
        if (templateVO != null) {
            String contentStr = BeanToXmlUtils.beanToXml(templateVO, TemplateVO.class);
            if (StringUtils.isNotEmpty(contentStr)) {
                this.setContent(contentStr);
            }
        }
        this.templateVO = templateVO;
    }

    /**
     * 模板ID
     */
    private Integer templateId;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板用途
     */
    private String purpose;
    /**
     * 模板内容
     */
    private String content;
    /**
     * 模板类型
     */
    private Integer type;
    /**
     * 模板类型名称
     */
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "type")
    private String typeName;
    /**
     * 模板是否使用
     */
    private String useFlag;


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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag == null ? null : useFlag.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}