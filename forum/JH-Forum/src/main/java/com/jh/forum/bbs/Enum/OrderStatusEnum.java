package com.jh.forum.bbs.Enum;

public enum OrderStatusEnum {
    Order_status_pay(1, "status_pay","已支付"),
    Order_status_noPay(0, "status_nopay","待支付");

    private Integer id;
    private String code;
    private String name;

    OrderStatusEnum(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
