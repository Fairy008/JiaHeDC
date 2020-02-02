package com.jh.produce.process.Enum;

/**
 * 算法类型
 * @version (1) 2018-03-16 cxj:created.
 */
public enum HandleConfigTypeEnum {
    IDL("IDL","","IDL",602),
    PYTHON("Python","","Python",601);

    private String key;
    private String value;
    private String text;
    private Integer dictId;

    private HandleConfigTypeEnum(String key, String handId, String text, Integer dictId){
        this.key = key;
        this.value = value;
        this.text = text;
        this.dictId = dictId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }
}
