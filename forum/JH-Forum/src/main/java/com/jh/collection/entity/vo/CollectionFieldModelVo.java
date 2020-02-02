package com.jh.collection.entity.vo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 字段模型实体
 * @version <1> 2018-11-15 xy： Created.
 */
public class CollectionFieldModelVo {

    private Integer id;
    private String fieldNameCh;//字段中文名
    private String fieldNameEn;//字段英文名
    private String fieldType;//字段类型
    private String calculation;//计算公司
    private String collectionValue;//采集值

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCollectionValue() {
        return collectionValue;
    }

    public void setCollectionValue(String collectionValue) {
        this.collectionValue = collectionValue;
    }

    @XmlAttribute(name="字段中文名")
    public String getFieldNameCh() {
        return fieldNameCh;
    }

    public void setFieldNameCh(String fieldNameCh) {
        this.fieldNameCh = fieldNameCh;
    }

    @XmlAttribute(name="字段英文名")
    public String getFieldNameEn() {
        return fieldNameEn;
    }

    public void setFieldNameEn(String fieldNameEn) {
        this.fieldNameEn = fieldNameEn;
    }

    public String getFieldType() {
        return fieldType;
    }

//    @XmlAttribute(name="字段类型")
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getCalculation() {
        return calculation;
    }

//    @XmlAttribute(name="计算公式")
    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }
}