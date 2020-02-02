package com.jh.data.enums;

/**
 * 任务执行状态
 * @version (1) 2018-03-15 cxj:created.
 */
public enum ProduceStatusEnum {
    NOACTIVE("noActive",1001,"未激活"),
    TOBEEXECUTED("toBeExecuted",1002,"待处理"),
    EXECUTING("executing",1003,"处理中"),
    SUCCESS("success",1004,"执行成功"),
    FAIL("fail",1005,"执行失败");

    private String key;
    private Integer value;
    private String text;

    private ProduceStatusEnum(String key, Integer value, String text){
        this.key = key;
        this.value = value;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}