package com.jh.produce.process.Enum;

/**
 * 算法输入输出关系
 * @version (1) 2018-03-16 cxj:created.
 */
public enum StepFileIoTypeEnum {
    INPUT(3001,"","输入"),
    OUTPUT(3002,"","输出");

    private Integer key;
    private String value;
    private String text;

    private StepFileIoTypeEnum(Integer key, String value, String text) {
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
