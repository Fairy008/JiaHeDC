package com.jh.produce.process.Enum;

/**
 * 算法输入输出关系
 * @version (1) 2018-03-16 cxj:created.
 */
public enum HandleConfigIoTypeEnum {
    ONETOONE(601,"1:1","一对一"),
    MANYTOONE(602,"n:1","多对一");

    private Integer key;
    private String value;
    private String text;

    private HandleConfigIoTypeEnum(Integer key, String value, String text) {
        this.key = key;
        this.value = value;
        this.text = text;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
