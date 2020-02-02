package com.jh.manage.alarm.Enum;

/**
 * Description: 告警状态枚举类
 *
 * @version <1> 2018-04-02 wl: Created
 */
public enum DataAlamStatusEnum {

    DATA_ALAM_SUCCESS("DATA_ALAM_SUCCESS","1","成功"),
    DATA_ALAM_FAIL("DATA_ALAM_FAIL","0","失败");


    private String key;

    private String value ;

    private String msg;


     DataAlamStatusEnum(String key, String value, String msg) {
        this.key = key;
        this.value = value;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
