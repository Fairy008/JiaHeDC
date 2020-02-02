package com.jh.manage.order.Enum;

/**
 * Description: 订单分类枚举类
 *
 * @version <1> 2018-03-17  lcw : Created.
 */
public enum OrderTypeEnum {

    DATA_ORDER_TYPE_SJDD(1301,"SJ",0,"数据订单"),
    DATA_ORDER_TYPE_XQDD(1302,"XQ",1,"需求订单");


    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    OrderTypeEnum(Integer id , String key, Integer value, String msg) {
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
