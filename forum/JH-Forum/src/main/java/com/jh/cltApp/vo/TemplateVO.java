package com.jh.cltApp.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 采集点数据模板XML VO
 * @version <1> 2019/4/9 15:54 zhangshen:Created.
 */
@XmlRootElement(name="attir")
public class TemplateVO {

    public TemplateVO(){

    }

    private List<FieldModelVO> fieldModelVOList;

    @XmlElement(name = "item")
    public List<FieldModelVO> getFieldModelVOList() {
        return fieldModelVOList;
    }

    public void setFieldModelVOList(List<FieldModelVO> fieldModelVOList) {
        this.fieldModelVOList = fieldModelVOList;
    }
}
