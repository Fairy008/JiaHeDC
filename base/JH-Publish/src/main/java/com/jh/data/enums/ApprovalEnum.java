package com.jh.data.enums;

/**
 * Description:
 * 1.报告审批状态
 *
 * @version <1> 2018-05-14 14:42 zhangshen: Created.
 */
public enum ApprovalEnum {
    NOT_APPROVAL("未审批","0"),
    APPROVAL_PASS("审批通过","1"),
    APPROVAL_NOT_PASS("审批未通过","2");

    private String name;
    private String value;

    ApprovalEnum(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
