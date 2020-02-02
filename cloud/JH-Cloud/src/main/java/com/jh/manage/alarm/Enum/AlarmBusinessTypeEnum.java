package com.jh.manage.alarm.Enum;

/**
 * 告警业务类型枚举类
 * Created by lcw on 2018-03-07
 */
public enum  AlarmBusinessTypeEnum {

    BUSINESS_TYPE_DOWNLOAD(1601,"BUSINESS_TYPE_DOWNLOAD",1,"数据下载"),
    BUSINESS_TYPE_LOADER(1602,"BUSINESS_TYPE_LOADER",2,"数据入库"),
    BUSINESS_TYPE_ARCHIVE(1603,"BUSINESS_TYPE_ARCHIVE",3,"数据归档"),
    BUSINESS_TYPE_ORDER(1604,"BUSINESS_TYPE_ORDER",4,"数据订单");
//    BUSINESS_TYPE_AUDITORDER(1605,"BUSINESS_TYPE_AUDITORDER",2,"auditOrder","审核订单"),
//    BUSINESS_TYPE_HANDLEODER(1606,"BUSINESS_TYPE_HANDLEODER",3,"handleOrder","分发订单"),
//    BUSINESS_TYPE_ORDERDOWNLOAD(1607,"BUSINESS_TYPE_ORDERDOWNLOAD",1,"OrderDataDownload","订单数据下载");

    private Integer id;
    private String key;
    private Integer value;
    private String msg;
    private AlarmBusinessTypeEnum(Integer id, String key,Integer value, String msg){
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
