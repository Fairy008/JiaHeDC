package com.jh.cltApp.vo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 字段模型VO
 * @version <1> 2019/4/9 15:54 zhangshen:Created.
 */
public class FieldModelVO {

    private String fieldNameCh;//字段中文名
    private String fieldNameEn;//字段英文名
    private String value;//采集值

    @XmlAttribute(name="fieldNameCh")
    public String getFieldNameCh() {
        return fieldNameCh;
    }

    public void setFieldNameCh(String fieldNameCh) {
        this.fieldNameCh = fieldNameCh;
    }

    @XmlAttribute(name="fieldNameEn")
    public String getFieldNameEn() {
        return fieldNameEn;
    }

    public void setFieldNameEn(String fieldNameEn) {
        this.fieldNameEn = fieldNameEn;
    }

    @XmlAttribute(name="value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
