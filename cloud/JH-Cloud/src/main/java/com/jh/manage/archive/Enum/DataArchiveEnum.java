package com.jh.manage.archive.Enum;

/**
 * Description: 归档状态枚举类
 *
 * @version <1> 2018-03-26 wl: Created
 */
public enum DataArchiveEnum {

    DATA_ARCHIVE_WAIT("DATA_ARCHIVE_WAIT",1501,"待归档"),
    DATA_ARCHIVE_IN("DATA_ARCHIVE_IN",1502,"归档中"),
    DATA_ARCHIVE_SUCCESS("DATA_ARCHIVE_SUCCESS",1503,"归档成功"),
    DATA_ARCHIVE_FAIL("DATA_ARCHIVE_FAIL",1504,"归档失败");


    private String key;

    private Integer value ;

    private String msg;


     DataArchiveEnum( String key, Integer value, String msg) {
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
