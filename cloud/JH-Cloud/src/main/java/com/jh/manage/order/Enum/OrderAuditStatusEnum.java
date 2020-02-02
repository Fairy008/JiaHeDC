package com.jh.manage.order.Enum;

/**
 * Description:审核状态枚举类
 *
 * @version <1> 2018-03-17  lcw : Created.
 */
public enum OrderAuditStatusEnum {

    DATA_AUDIT_STATUS_DSH(1101,"DATA_AUDIT_STATUS_DSH",0,"待审核"),
    DATA_AUDIT_STATUS_DFF(1103,"DATA_AUDIT_STATUS_DFF",2,"待分发"),
    DATA_AUDIT_STATUS_SHBTG(1102,"DATA_AUDIT_STATUS_SHBTG",1,"审核不通过"),
    DATA_AUDIT_STATUS_YFF(1104,"DATA_AUDIT_STATUS_YFF",3,"已分发"),
    DATA_AUDIT_STATUS_JJFF(1105,"DATA_AUDIT_STATUS_JJFF",4,"拒绝分发");


    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    OrderAuditStatusEnum(Integer id , String key, Integer value, String msg) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
