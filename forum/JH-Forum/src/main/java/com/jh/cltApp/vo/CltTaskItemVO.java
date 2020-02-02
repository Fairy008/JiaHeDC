package com.jh.cltApp.vo;

import com.jh.cltApp.entity.CltMediaSource;
import com.jh.cltApp.entity.CltTaskItem;
import com.jh.cltApp.utils.BeanToXmlUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CltTaskItemVO extends CltTaskItem {

    private List<CltMediaSource> cltMediaSourceList;//媒体信息列表

    private TemplateVO templateVO;

    public TemplateVO getTemplateVO() {
        if (StringUtils.isNotEmpty(this.getContentXml())) {
            templateVO = BeanToXmlUtils.xml2Bean(TemplateVO.class, this.getContentXml());
        }
        return templateVO;
    }

    public void setTemplateVO(TemplateVO templateVO) {
        if (templateVO != null && templateVO.getFieldModelVOList() != null && templateVO.getFieldModelVOList().size() > 0) {
            String contentXml = BeanToXmlUtils.beanToXml(templateVO, TemplateVO.class);
            this.setContentXml(contentXml);
        }
        this.templateVO = templateVO;
    }

    private Integer accountId;//参与人id

    private String isShoulder;//是否是负责人

    private String isCreator;//是否是创建人

    private String surveyTimeStr;//调查时间

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getIsShoulder() {
        return isShoulder;
    }

    public void setIsShoulder(String isShoulder) {
        this.isShoulder = isShoulder;
    }

    public List<CltMediaSource> getCltMediaSourceList() {
        return cltMediaSourceList;
    }

    public void setCltMediaSourceList(List<CltMediaSource> cltMediaSourceList) {
        this.cltMediaSourceList = cltMediaSourceList;
    }

    public String getSurveyTimeStr() {
        return surveyTimeStr;
    }

    public void setSurveyTimeStr(String surveyTimeStr) {
        this.surveyTimeStr = surveyTimeStr;
    }

    public String getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(String isCreator) {
        this.isCreator = isCreator;
    }
}
