package com.jh.manage.order.Enum;

/**
 * Description:数据分发状态枚举类
 *
 * @version <1> 2018-03-17  lcw : Created.
 */
public enum OrderHandleStatusEnum {

    DATA_HANDLE_STATUS_DFF(1201,"DATA_HANDLE_STATUS_DFF",0,"待分发"),
    DATA_HANDLE_STATUS_YFF(1202,"DATA_HANDLE_STATUS_YFF",1,"已分发"),
    DATA_LOADER_STATUS_JJFF(1203,"DATA_LOADER_STATUS_JJFF",2,"拒绝分发");


    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    OrderHandleStatusEnum(Integer id , String key, Integer value, String msg) {
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
