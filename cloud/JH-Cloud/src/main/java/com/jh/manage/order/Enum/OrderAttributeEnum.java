package com.jh.manage.order.Enum;

/**
 * Description: 订单分类枚举类
 *
 * @version <1> 2018-03-17  lcw : Created.
 */
public enum OrderAttributeEnum {

    DATA_ORDER_ATTRIBUTE_INNER(2101,"INNER",0,"内部订单"),
    DATA_ORDER_ATTRIBUTE_OUTER(2102,"OUTER",1,"外部订单");


    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    OrderAttributeEnum(Integer id , String key, Integer value, String msg) {
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
